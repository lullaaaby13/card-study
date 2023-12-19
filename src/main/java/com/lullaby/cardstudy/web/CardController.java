package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.AddCardCommand;
import com.lullaby.cardstudy.appliation.CardService;
import com.lullaby.cardstudy.appliation.UpdateCardCommand;
import com.lullaby.cardstudy.dto.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/cards")
@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    @GetMapping
    public List<CardResponse> getCards(@RequestParam(name = "categoryId") Long categoryId) {
        return cardService.getCards(categoryId);
    }

    @PostMapping
    public CardResponse addCard(@RequestBody AddCardCommand command) {
        return cardService.addCard(command);
    }

    @DeleteMapping
    public void deleteCard(@RequestParam Long id) {
        cardService.deleteCard(id);
    }

    @PutMapping("{id}")
    public void updateCard(@PathVariable Long id,
                           @RequestBody UpdateCardCommand command) {
        cardService.updateCard(id, command);
    }

}

