package com.lullaby.cardstudy.appliation.card.word;

import com.lullaby.cardstudy.appliation.card.word.dto.CreateWordCardCommand;
import com.lullaby.cardstudy.appliation.card.word.dto.UpdateWordCardCommand;
import com.lullaby.cardstudy.appliation.card.word.dto.WordCardResponse;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.domain.card.word.WordCard;
import com.lullaby.cardstudy.domain.card.word.WordCardRepository;
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
import java.util.stream.Stream;


@Transactional
@RequiredArgsConstructor
@Service
public class WordCardService {

    private final WordCardRepository wordCardRepository;
    private final CardSetService cardSetService;
    private final MemberService memberService;

    public List<WordCardResponse> getCards(Long userId, Long cardSetId) {
        CardSet cardSet = cardSetService.findCardSetEntityOrElseThrow(cardSetId, userId);

        return wordCardRepository.findByCardSetOrderByIdDesc(cardSet)
                .stream().map(WordCardResponse::new)
                .toList();
    }

    public WordCardResponse addCard(Long userId, CreateWordCardCommand command) {
        CardSet cardSet = cardSetService.findCardSetEntityOrElseThrow(command.cardSetId(), userId);
        validateCardOwner(userId, cardSet);

        if (wordCardRepository.existsByCardSetAndQuestion(cardSet, command.question())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "이미 존재하는 질문입니다.");
        }

        WordCard card = new WordCard(cardSet, command.question(), command.answer());
        wordCardRepository.save(card);
        cardSet.increaseCardCount();
        return new WordCardResponse(card);
    }

    public void deleteCard(Long userId, Long id) {
        WordCard card = findWordCardEntityOrElseThrow(id);
        validateCardOwner(userId, card.getCardSet());
        card.getCardSet().decreaseCardCount();
        wordCardRepository.delete(card);
    }

    public WordCardResponse updateCard(Long userId, Long id, UpdateWordCardCommand command) {
        WordCard card = findWordCardEntityOrElseThrow(id);
        validateCardOwner(userId, card.getCardSet());

        if (command.question() != null) {
            card.setQuestion(command.question());
        }
        if (command.answer() != null) {
            card.setAnswer(command.answer());
        }
        card.clearMemorizationLevel();
        return new WordCardResponse(card);
    }

    public List<WordCardResponse> addCardByFile(Long userId, Long cardSetId, String textFileContent) {
        List<WordCardResponse> responses = new ArrayList<>();

        Stream.of(textFileContent.split("@@"))
                .map(StringUtils::trimToEmpty)
                .filter(StringUtils::isNotBlank)
                .forEach(text -> {
                    String question = StringUtils.substringBefore(text, "\n").trim();
                    String answer = StringUtils.substringAfter(text, "\n").trim();
                    try {
                        WordCardResponse response = addCard(userId, new CreateWordCardCommand(cardSetId, question, answer));
                        responses.add(response);
                    } catch (HttpClientErrorException exception) {
                        if (exception.getStatusCode() != HttpStatus.BAD_REQUEST) {
                            throw exception;
                        }
                    }
                });

        return responses;
    }

    private WordCard findWordCardEntityOrElseThrow(Long id) {
        return wordCardRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."));
    }

    private void validateCardOwner(Long userId, CardSet cardSet) {
        Member member = memberService.findEntityOrElseThrow(userId);
        if (!cardSet.isOwnedBy(member)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "카드 소유자만 카드를 추가/변경/삭제 할 수 있습니다.");
        }
    }

}
