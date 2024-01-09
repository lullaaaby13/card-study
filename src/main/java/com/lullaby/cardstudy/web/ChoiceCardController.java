package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.authenticate.dto.AuthenticatedUser;
import com.lullaby.cardstudy.appliation.card.choice.ChoiceCardService;
import com.lullaby.cardstudy.appliation.card.choice.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/choice-cards")
@RequiredArgsConstructor
@RestController
public class ChoiceCardController {

    private final ChoiceCardService cardService;

    @GetMapping
    public List<ChoiceCardResponse> list(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                         @RequestParam(name = "cardSetId") Long cardSetId) {

        return cardService.list(authenticatedUser.getUserId(), cardSetId);
    }

    @PostMapping
    public ChoiceCardResponse create(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody CreateChoiceCardCommand command) {
        return cardService.create(authenticatedUser.getUserId(), command);
    }

    @DeleteMapping("{id}")
    public void delete(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id) {
        cardService.delete(authenticatedUser.getUserId(), id);
    }

    @PatchMapping("{id}")
    public ChoiceCardResponse update(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateChoiceCardCommand command) {
        return cardService.update(authenticatedUser.getUserId(), id, command);
    }

    @PatchMapping("{id}/choice")
    public ChoiceCardResponse updateChoice(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateChoiceCommand command) {
        return cardService.updateChoice(authenticatedUser.getUserId(), id, command);
    }

    @PutMapping("{id}/choice/sequence")
    public ChoiceCardResponse changeChoiceSequence(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable(name = "id") Long id,
            @RequestBody ChangeChoiceSequenceCommand command) {
        return cardService.changeChoiceSequence(authenticatedUser.getUserId(), id, command);
    }

//    @PostMapping("/file")
//    public List<WordCardResponse> addCardByFile(
//            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
//            @RequestParam Long cardSetId,
//            @RequestPart("file") MultipartFile multipartFile
//    ) throws IOException {
//        return cardService.addCardByFile(authenticatedUser.getUserId(), cardSetId, new String(multipartFile.getBytes()));
//    }


}
