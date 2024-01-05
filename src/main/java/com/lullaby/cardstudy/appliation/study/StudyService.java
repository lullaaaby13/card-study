package com.lullaby.cardstudy.appliation.study;

import com.lullaby.cardstudy.appliation.study.dto.AddStudyCommand;
import com.lullaby.cardstudy.common.exception.NotFoundException;
import com.lullaby.cardstudy.domain.card.Card;
import com.lullaby.cardstudy.domain.card.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class StudyService {

    private final CardRepository cardRepository;


    public void addStudy(AddStudyCommand command) {
        Card card = cardRepository.findById(command.cardId())
                .orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다."));

        if (command.studyResult() == AddStudyCommand.StudyResult.CORRECT) {
            card.increaseMemorizationLevel();
        } else if (command.studyResult() == AddStudyCommand.StudyResult.CORRECT) {
            card.decreaseMemorizationLevel();
        }

    }

}
