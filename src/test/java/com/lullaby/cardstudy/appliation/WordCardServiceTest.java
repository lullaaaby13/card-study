package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.appliation.card.word.WordCardService;
import com.lullaby.cardstudy.appliation.card.word.dto.CreateWordCardCommand;
import com.lullaby.cardstudy.appliation.card.word.dto.UpdateWordCardCommand;
import com.lullaby.cardstudy.appliation.card.word.dto.WordCardResponse;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import com.lullaby.cardstudy.domain.card.MemorizationLevel;
import com.lullaby.cardstudy.domain.cardset.CardSetType;
import com.lullaby.cardstudy.domain.member.Member;
import com.lullaby.cardstudy.fixture.MemberFixture;
import com.lullaby.cardstudy.utils.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("단어 카드 서비스 테스트")
class WordCardServiceTest extends IntegrationTest {

    @Autowired
    WordCardService cardService;

    @Autowired
    CardSetService cardSetService;

    Member member = MemberFixture.MEMBER;
    CardSetResponse cardSet;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
        cardSet = cardSetService.addCardSet(member.getId(), new AddCardSetCommand(CardSetType.WORD, "word", null));
    }

    @DisplayName("단어 카드를 생성 한다.")
    @Test
    void addWordCard() {
        // given
        CreateWordCardCommand addWordCardCommand = new CreateWordCardCommand(cardSet.id(), "question", "answer");

        // when
        WordCardResponse wordCardResponse = cardService.addCard(member.getId(), addWordCardCommand);

        // then
        assertThat(wordCardResponse).isNotNull();
    }

    @DisplayName("단어 카드를 조회 한다.")
    @Test
    void getWordCard() {
        // given
        CreateWordCardCommand addWordCardCommand = new CreateWordCardCommand(cardSet.id(), "question", "answer");
        cardService.addCard(member.getId(), addWordCardCommand);

        // when
        List<WordCardResponse> wordCards = cardService.getCards(member.getId(), cardSet.id());

        // then
        assertThat(wordCards.get(0)).isNotNull();
    }

    @DisplayName("단어 카드를 삭제 한다.")
    @Test
    void deleteWordCard() {
        // given
        CreateWordCardCommand addWordCardCommand = new CreateWordCardCommand(cardSet.id(), "question", "answer");
        WordCardResponse wordCardResponse = cardService.addCard(member.getId(), addWordCardCommand);

        // when
        cardService.deleteCard(member.getId(), wordCardResponse.id());

        // then
        List<WordCardResponse> wordCards = cardService.getCards(member.getId(), cardSet.id());
        assertThat(wordCards).isEmpty();
    }

    @DisplayName("단어 카드를 수정 한다.")
    @Test
    void updateWordCard() {
        // given
        CreateWordCardCommand addWordCardCommand = new CreateWordCardCommand(cardSet.id(), "question", "answer");
        WordCardResponse wordCardResponse = cardService.addCard(member.getId(), addWordCardCommand);

        // when
        UpdateWordCardCommand updateWordCardCommand = new UpdateWordCardCommand("question2", "answer2");
        cardService.updateCard(member.getId(), wordCardResponse.id(), updateWordCardCommand);

        // then
        WordCardResponse wordCard = cardService.getCards(member.getId(), cardSet.id()).get(0);
        assertThat(wordCard.question()).isEqualTo("question2");
        assertThat(wordCard.answer()).isEqualTo("answer2");
        assertThat(wordCard.memorizationLevel()).isEqualTo(MemorizationLevel.Difficult);
    }

    @DisplayName("존재 하지 않는 카드를 수정 시도하면 수정에 실패 한다.")
    @Test
    void updateWordCardFail() {
        // given
        CreateWordCardCommand addWordCardCommand = new CreateWordCardCommand(cardSet.id(), "question", "answer");
        cardService.addCard(member.getId(), addWordCardCommand);

        // when & then
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            UpdateWordCardCommand updateWordCardCommand = new UpdateWordCardCommand("question2", "answer2");
            cardService.updateCard(member.getId(), Long.MAX_VALUE, updateWordCardCommand);
        });
    }

    @Test
    void name() {

        CardSetResponse cardSet = cardSetService.addCardSet(member.getId(), new AddCardSetCommand(CardSetType.WORD, "test", null));

        String textContent = """
                Apple
                사과
                @@
                Banana
                바나나
                뻐내너
                @@
                Citron
                유자
                """;
        cardService.addCardByFile(member.getId(), cardSet.id(), textContent);
    }
}
