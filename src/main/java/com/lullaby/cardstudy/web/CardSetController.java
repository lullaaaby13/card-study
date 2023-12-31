package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.cardset.dto.UpdateCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/card-sets")
@RequiredArgsConstructor
@RestController
public class CardSetController {

    private final CardSetService cardSetService;

    @GetMapping
    public List<CardSetResponse> getCardSets() {
        return cardSetService.getCardSets();
    }

    @PostMapping
    public CardSetResponse addCardSet(@RequestBody AddCardSetCommand command) {
        return cardSetService.addCardSet(command);
    }

    @DeleteMapping("/{id}")
    public void deleteCardSet(@PathVariable(name = "id") Long id) {
        cardSetService.deleteCardSet(id);
    }

    @PutMapping("/{id}")
    public CardSetResponse updateCardSet(@PathVariable(name = "id") Long id, @RequestBody UpdateCardSetCommand command) {
        return cardSetService.updateCardSet(id, command);
    }

}
