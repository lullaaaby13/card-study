package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import com.lullaby.cardstudy.appliation.card.CardService;
import com.lullaby.cardstudy.appliation.card.dto.AddCardCommand;
import com.lullaby.cardstudy.appliation.card.dto.CardResponse;
import com.lullaby.cardstudy.appliation.card.dto.UpdateCardCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/cards")
@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    @GetMapping
    public List<CardResponse> getCards(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestParam(name = "cardSetId") Long cardSetId) {

        return cardService.getCards(authenticatedUser.getUserId(), cardSetId);
    }

    @PostMapping
    public CardResponse addCard(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody AddCardCommand command) {
        return cardService.addCard(authenticatedUser.getUserId(), command);
    }

    @DeleteMapping("{id}")
    public void deleteCard(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id) {
        cardService.deleteCard(authenticatedUser.getUserId(), id);
    }

    @PatchMapping("{id}")
    public CardResponse updateCard(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateCardCommand command) {
        return cardService.updateCard(authenticatedUser.getUserId(), id, command);
    }

    @PostMapping("/file")
    public List<CardResponse> addCardByFile(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam Long cardSetId,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
        return cardService.addCardByFile(authenticatedUser.getUserId(), cardSetId, new String(multipartFile.getBytes()));
    }

}


