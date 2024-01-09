package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import com.lullaby.cardstudy.appliation.card.word.WordCardService;
import com.lullaby.cardstudy.appliation.card.word.dto.CreateWordCardCommand;
import com.lullaby.cardstudy.appliation.card.word.dto.WordCardResponse;
import com.lullaby.cardstudy.appliation.card.word.dto.UpdateWordCardCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/word-cards")
@RequiredArgsConstructor
@RestController
public class WordCardController {

    private final WordCardService cardService;

    @GetMapping
    public List<WordCardResponse> getCards(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                           @RequestParam(name = "cardSetId") Long cardSetId) {

        return cardService.getCards(authenticatedUser.getUserId(), cardSetId);
    }

    @PostMapping
    public WordCardResponse addCard(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody CreateWordCardCommand command) {
        return cardService.addCard(authenticatedUser.getUserId(), command);
    }

    @DeleteMapping("{id}")
    public void deleteCard(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id) {
        cardService.deleteCard(authenticatedUser.getUserId(), id);
    }

    @PatchMapping("{id}")
    public WordCardResponse updateCard(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateWordCardCommand command) {
        return cardService.updateCard(authenticatedUser.getUserId(), id, command);
    }

    @PostMapping("/file")
    public List<WordCardResponse> addCardByFile(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam Long cardSetId,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
        return cardService.addCardByFile(authenticatedUser.getUserId(), cardSetId, new String(multipartFile.getBytes()));
    }


}


