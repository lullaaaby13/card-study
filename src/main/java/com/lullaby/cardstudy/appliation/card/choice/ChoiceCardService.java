package com.lullaby.cardstudy.appliation.card.choice;

import com.lullaby.cardstudy.appliation.card.choice.dto.*;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.domain.card.choice.ChoiceCard;
import com.lullaby.cardstudy.domain.card.choice.ChoiceCardRepository;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import com.lullaby.cardstudy.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class ChoiceCardService {

    private final ChoiceCardRepository choiceCardRepository;
    private final CardSetService cardSetService;
    private final MemberService memberService;

    public List<ChoiceCardResponse> list(Long userId, Long cardSetId) {
        CardSet cardSet = cardSetService.findCardSetEntityOrElseThrow(cardSetId, userId);

        return choiceCardRepository.findByCardSetOrderByIdDesc(cardSet)
                .stream().map(ChoiceCardResponse::new)
                .toList();
    }

    public ChoiceCardResponse create(Long userId, CreateChoiceCardCommand command) {
        CardSet cardSet = cardSetService.findCardSetEntityOrElseThrow(command.cardSetId(), userId);

        ChoiceCard card = new ChoiceCard(cardSet, command.question(), command.answer());
        command.choices().forEach(choice -> card.addChoice(choice.sequence(), choice.content(), choice.isAnswer()));
        choiceCardRepository.save(card);
        cardSet.increaseCardCount();
        return new ChoiceCardResponse(card);
    }

    public void delete(Long userId, Long id) {
        ChoiceCard card = findEntityOrElseThrow(id);
        CardSet cardSet = card.getCardSet();
        validateOwner(userId, cardSet);
        cardSet.decreaseCardCount();
        choiceCardRepository.delete(card);
    }

    public ChoiceCard findEntityOrElseThrow(Long id) {
        return choiceCardRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."));
    }

    public ChoiceCardResponse update(Long userId, Long id, UpdateChoiceCardCommand command) {
        ChoiceCard card = findEntityOrElseThrow(id);
        validateOwner(userId, card.getCardSet());

        if (command.question() != null) {
            card.setQuestion(command.question());
        }
        if (command.answer() != null) {
            card.setAnswer(command.answer());
        }
        card.clearMemorizationLevel();
        return new ChoiceCardResponse(card);
    }

    public ChoiceCardResponse updateChoice(Long userId, Long id, UpdateChoiceCommand command) {
        ChoiceCard card = findEntityOrElseThrow(id);
        validateOwner(userId, card.getCardSet());

        card.updateChoice(command.sequence(), command.content(), command.isAnswer());


        card.clearMemorizationLevel();
        return new ChoiceCardResponse(card);
    }

    public ChoiceCardResponse changeChoiceSequence(Long userId, Long id, ChangeChoiceSequenceCommand command) {
        ChoiceCard card = findEntityOrElseThrow(id);
        validateOwner(userId, card.getCardSet());

        card.changeChoiceSequence(command.sequence(), command.newSequence());
        card.clearMemorizationLevel();
        return new ChoiceCardResponse(card);
    }

    public List<ChoiceCardResponse> createByFile(Long userId, Long cardSetId, String textFileContent) {
        List<ChoiceCardResponse> responses = new ArrayList<>();

        for (String problemTemplate : textFileContent.split("@@")) {

            String[] problem = problemTemplate.split("Q.|1.|2.|3.|4.|5.|A.");

            ChoiceCardResponse response = create(userId, null);
            responses.add(response);
        }

        return responses;
    }

    private void validateOwner(Long userId, CardSet cardSet) {
        Member member = memberService.findEntityOrElseThrow(userId);
        if (!cardSet.isOwnedBy(member)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "카드 소유자만 카드를 추가/수정/삭제 할 수 있습니다.");
        }
    }

}
