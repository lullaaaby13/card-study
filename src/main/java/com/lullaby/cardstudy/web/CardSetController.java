package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.cardset.dto.UpdateCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/card-sets")
@RequiredArgsConstructor
@RestController
public class CardSetController {

    private final CardSetService cardSetService;

    @GetMapping
    public List<CardSetResponse> getCardSets(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return cardSetService.getCardSets(authenticatedUser.getUserId());
    }

    @PostMapping
    public CardSetResponse addCardSet(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody AddCardSetCommand command) {
        return cardSetService.addCardSet(authenticatedUser.getUserId(), command);
    }

    @DeleteMapping("/{id}")
    public void deleteCardSet(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id) {
        cardSetService.deleteCardSet(authenticatedUser.getUserId(), id);
    }

    @PutMapping("/{id}")
    public CardSetResponse updateCardSet(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateCardSetCommand command) {
        return cardSetService.updateCardSet(authenticatedUser.getUserId(), id, command);
    }

}
