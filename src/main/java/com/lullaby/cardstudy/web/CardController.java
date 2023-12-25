package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.AddCardCommand;
import com.lullaby.cardstudy.appliation.CardService;
import com.lullaby.cardstudy.appliation.UpdateCardCommand;
import com.lullaby.cardstudy.dto.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    @GetMapping
    public List<CardResponse> getCards(@RequestParam(name = "cardSetId") Long cardSetId) {
        return cardService.getCards(cardSetId);
    }

    @PostMapping
    public CardResponse addCard(@RequestBody AddCardCommand command) {
        return cardService.addCard(command);
    }

    @DeleteMapping("{id}")
    public void deleteCard(@PathVariable(name = "id") Long id) {
        cardService.deleteCard(id);
    }

    @PutMapping("{id}")
    public void updateCard(@PathVariable(name = "id") Long id,
                           @RequestBody UpdateCardCommand command) {
        cardService.updateCard(id, command);
    }

}

