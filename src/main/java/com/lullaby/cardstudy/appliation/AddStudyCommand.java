package com.lullaby.cardstudy.appliation;

import java.time.LocalDateTime;

public record AddStudyCommand(
        Long cardId,
        StudyResult studyResult,
        LocalDateTime viewFrontAt,
        LocalDateTime viewBackAt

) {

    enum StudyResult {
        CORRECT,
        WRONG
    }
}
