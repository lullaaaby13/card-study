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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

            List<String> fragments = Stream.of(problemTemplate.split("\\$\\$"))
                    .filter(StringUtils::isNotBlank)
                    .toList();

            if (fragments.isEmpty()) {
                continue;
            }

            CreateChoiceCardCommand createChoiceCardCommand = new CreateChoiceCardCommand(
                    cardSetId,
                    fragments.stream()
                            .filter(fragment -> fragment.startsWith("Q."))
                            .findFirst()
                            .orElse(null),
                    fragments.stream()
                            .filter(fragment -> fragment.startsWith("A."))
                            .findFirst()
                            .orElse(null),
                    fragments.stream()
                            .filter(fragment -> fragment.startsWith("1.") || fragment.startsWith("2.") || fragment.startsWith("3.") || fragment.startsWith("4.") || fragment.startsWith("5."))
                            .map(it -> new CreateChoiceCardCommand.Choice(
                                    Integer.parseInt(it.substring(0, 1))
                                    , it.substring(2).replace("[X]", "").replace("[O]", "").trim()
                                    , it.contains("[O]")
                            )).toList()
            );

            ChoiceCardResponse response = create(userId, createChoiceCardCommand);
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
