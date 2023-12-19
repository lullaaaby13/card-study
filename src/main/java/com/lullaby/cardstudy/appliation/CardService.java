package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.domain.Card;
import com.lullaby.cardstudy.domain.CardRepository;
import com.lullaby.cardstudy.domain.Category;
import com.lullaby.cardstudy.dto.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CategoryService categoryService;

    public List<CardResponse> getCards(Long categoryId) {
        Category category = categoryService.findCategory(categoryId)
                .orElseThrow(RuntimeException::new);

        return cardRepository.findByCategory(category)
                .stream().map(CardResponse::new)
                .toList();
    }

    public CardResponse addCard(AddCardCommand command) {
        Category category = categoryService.findCategory(command.categoryId())
                .orElseThrow(RuntimeException::new);

        Card card = new Card(category, command.front(), command.back());
        cardRepository.save(card);
        return new CardResponse(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    public void updateCard(Long id, UpdateCardCommand command) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.updateFront(command.front());
        card.updateBack(command.back());
        cardRepository.save(card);
    }

    public void increaseMemorizationLevel(Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.increaseMemorizationLevel();
        cardRepository.save(card);
    }

    public void decreaseMemorizationLevel(Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.decreaseMemorizationLevel();
        cardRepository.save(card);
    }

}
