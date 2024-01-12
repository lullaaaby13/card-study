package com.lullaby.cardstudy.appliation.study;

import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.study.dto.AddStudyCommand;
import com.lullaby.cardstudy.domain.card.Card;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Transactional
@RequiredArgsConstructor
@Service
public class StudyService {

    private final CardSetService cardSetService;

    public void addStudy(Long userId, AddStudyCommand command) {
        CardSet cardSet = cardSetService.findCardWithinCardSet(command.cardSetId(), command.cardId(), userId);
        Card card = cardSet.firstCard()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."));

        if (command.studyResult() == AddStudyCommand.StudyResult.CORRECT) {
            card.increaseMemorizationLevel();
        } else if (command.studyResult() == AddStudyCommand.StudyResult.CORRECT) {
            card.decreaseMemorizationLevel();
        }
    }

}