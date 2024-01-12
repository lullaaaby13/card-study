package com.lullaby.cardstudy.appliation.study.dto;

import java.time.LocalDateTime;

public record AddStudyCommand(
        Long cardSetId,
        Long cardId,
        StudyResult studyResult,
        LocalDateTime viewFrontAt,
        LocalDateTime viewBackAt

) {

    public enum StudyResult {
        CORRECT,
        WRONG
    }
}
