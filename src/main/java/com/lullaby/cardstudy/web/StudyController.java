package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import com.lullaby.cardstudy.appliation.study.StudyService;
import com.lullaby.cardstudy.appliation.study.dto.AddStudyCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/study")
@RequiredArgsConstructor
@RestController
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public void addStudy(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                         @RequestBody AddStudyCommand command) {
        studyService.addStudy(authenticatedUser.getUserId(), command);
    }


}
