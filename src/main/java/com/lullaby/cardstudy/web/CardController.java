package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.AddCardCommand;
import com.lullaby.cardstudy.appliation.CardService;
import com.lullaby.cardstudy.appliation.UpdateCardCommand;
import com.lullaby.cardstudy.dto.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PatchMapping("{id}")
    public CardResponse updateCard(@PathVariable(name = "id") Long id,
                                   @RequestBody UpdateCardCommand command) {
        return cardService.updateCard(id, command);
    }

    @PostMapping("/file")
    public List<CardResponse> addCardByFile(
            @RequestParam Long cardSetId,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
        return cardService.addCardByFile(cardSetId, new String(multipartFile.getBytes()));
    }

}


