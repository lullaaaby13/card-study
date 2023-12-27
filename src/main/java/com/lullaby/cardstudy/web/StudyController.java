package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.AddStudyCommand;
import com.lullaby.cardstudy.appliation.CardService;
import com.lullaby.cardstudy.appliation.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/study")
@RequiredArgsConstructor
@RestController
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public void addStudy(@RequestBody AddStudyCommand command) {
        studyService.addStudy(command);
    }


}
