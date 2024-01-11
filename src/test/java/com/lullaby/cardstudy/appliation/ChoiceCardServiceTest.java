package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.appliation.card.choice.ChoiceCardService;
import com.lullaby.cardstudy.appliation.card.choice.dto.CreateChoiceCardCommand;
import com.lullaby.cardstudy.appliation.card.choice.dto.ChoiceCardResponse;
import com.lullaby.cardstudy.appliation.card.choice.dto.UpdateChoiceCardCommand;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import com.lullaby.cardstudy.domain.cardset.CardSetType;
import com.lullaby.cardstudy.domain.member.Member;
import com.lullaby.cardstudy.fixture.MemberFixture;
import com.lullaby.cardstudy.utils.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("선다형 카드 서비스 테스트")
class ChoiceCardServiceTest extends IntegrationTest {

    @Autowired
    ChoiceCardService cardService;

    @Autowired
    CardSetService cardSetService;

    Member member = MemberFixture.MEMBER;
    CardSetResponse cardSet;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
        cardSet = cardSetService.addCardSet(member.getId(), new AddCardSetCommand(CardSetType.CHOICE, "choice", null));
    }

    @DisplayName("단어 카드를 생성 한다.")
    @Test
    void add() {
        // given
        CreateChoiceCardCommand createChoiceCardCommand = new CreateChoiceCardCommand(
                cardSet.id()
                ,"question"
                , "answer"
                , List.of(
                    new CreateChoiceCardCommand.Choice(1, "content", true)
                    , new CreateChoiceCardCommand.Choice(2, "content2", false)
                    , new CreateChoiceCardCommand.Choice(3, "content3", false)
                    , new CreateChoiceCardCommand.Choice(4, "content4", false)
                    , new CreateChoiceCardCommand.Choice(5, "content5", false)
            ));

        // when
        ChoiceCardResponse response = cardService.create(member.getId(), createChoiceCardCommand);

        // then
        assertThat(response).isNotNull();
    }

    @DisplayName("단어 카드를 조회 한다.")
    @Test
    void list() {
        // given
        CreateChoiceCardCommand createChoiceCardCommand = new CreateChoiceCardCommand(
                cardSet.id()
                ,"question"
                , "answer"
                , List.of(
                new CreateChoiceCardCommand.Choice(1, "content", true)
                , new CreateChoiceCardCommand.Choice(2, "content2", false)
                , new CreateChoiceCardCommand.Choice(3, "content3", false)
                , new CreateChoiceCardCommand.Choice(4, "content4", false)
                , new CreateChoiceCardCommand.Choice(5, "content5", false)
        ));
        cardService.create(member.getId(), createChoiceCardCommand);

        // when
        List<ChoiceCardResponse> responses = cardService.list(member.getId(), cardSet.id());

        // then
        assertThat(responses).isNotEmpty();
    }

    @DisplayName("단어 카드를 삭제 한다.")
    @Test
    void delete() {
        // given
        CreateChoiceCardCommand createChoiceCardCommand = new CreateChoiceCardCommand(
                cardSet.id()
                ,"question"
                , "answer"
                , List.of(
                new CreateChoiceCardCommand.Choice(1, "content", true)
                , new CreateChoiceCardCommand.Choice(2, "content2", false)
                , new CreateChoiceCardCommand.Choice(3, "content3", false)
                , new CreateChoiceCardCommand.Choice(4, "content4", false)
                , new CreateChoiceCardCommand.Choice(5, "content5", false)
        ));
        ChoiceCardResponse cardResponse = cardService.create(member.getId(), createChoiceCardCommand);

        // when
        cardService.delete(member.getId(), cardResponse.id());

        // then
        List<ChoiceCardResponse> responses = cardService.list(member.getId(), cardSet.id());
        assertThat(responses).isEmpty();
    }

    @DisplayName("단어 카드를 수정 한다.")
    @Test
    void update() {
        // given
        CreateChoiceCardCommand createChoiceCardCommand = new CreateChoiceCardCommand(
                cardSet.id()
                ,"question"
                , "answer"
                , List.of(
                new CreateChoiceCardCommand.Choice(1, "content", true)
                , new CreateChoiceCardCommand.Choice(2, "content2", false)
                , new CreateChoiceCardCommand.Choice(3, "content3", false)
                , new CreateChoiceCardCommand.Choice(4, "content4", false)
                , new CreateChoiceCardCommand.Choice(5, "content5", false)
        ));
        ChoiceCardResponse cardResponse = cardService.create(member.getId(), createChoiceCardCommand);

        // when
        UpdateChoiceCardCommand updateChoiceCardCommand = new UpdateChoiceCardCommand("question2", "answer2");
        cardService.update(member.getId(), cardResponse.id(), updateChoiceCardCommand);

        // then
        List<ChoiceCardResponse> responses = cardService.list(member.getId(), cardSet.id());
        assertThat(responses.get(0).question()).isEqualTo("question2");
        assertThat(responses.get(0).answer()).isEqualTo("answer2");
    }

//    @DisplayName("존재 하지 않는 카드를 수정 시도하면 수정에 실패 한다.")
//    @Test
//    void updateWordCardFail() {
//        // given
//        // given
//        AddChoiceCardCommand addChoiceCardCommand = new AddChoiceCardCommand(
//                cardSet.id()
//                ,"question"
//                , "answer"
//                , List.of(
//                new AddChoiceCardCommand.Choice(1, "content", true)
//                , new AddChoiceCardCommand.Choice(2, "content2", false)
//                , new AddChoiceCardCommand.Choice(3, "content3", false)
//                , new AddChoiceCardCommand.Choice(4, "content4", false)
//                , new AddChoiceCardCommand.Choice(5, "content5", false)
//        ));
//        ChoiceCardResponse cardResponse = cardService.add(member.getId(), addChoiceCardCommand);
//
//
//        // when & then
//        assertThrows(HttpClientErrorException.class, () -> {
//            UpdateWordCardCommand updateWordCardCommand = new UpdateWordCardCommand("question2", "answer2");
//            cardService.updateCard(member.getId(), Long.MAX_VALUE, updateWordCardCommand);
//        });
//    }

    @Test
    void createByFile() {
        String content = """
                $$Q. Customer reviews indicate that many modern mobile devices are often unnecessarily ____.
  $$1. complication [O]
  $$2. complicates [X]
  $$3. complicate [X]
  $$4. complicated [X]
  $$A. Answer of the question.
  @@
  $$Q. Customer reviews indicate that many modern mobile devices are often unnecessarily ____.
  $$1. complication [O]
  $$2. complicates [X]
  $$3. complicate [X]
  $$4. complicated [X]
  $$A. Answer of the question. 
  @@
                """;
        List<ChoiceCardResponse> responses = cardService.createByFile(member.getId(), cardSet.id(), content);
        assertThat(responses.size()).isEqualTo(2);
    }


}
