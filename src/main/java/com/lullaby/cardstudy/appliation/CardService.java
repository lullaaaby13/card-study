package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.common.exception.NotFoundException;
import com.lullaby.cardstudy.domain.Card;
import com.lullaby.cardstudy.domain.CardRepository;
import com.lullaby.cardstudy.domain.CardSet;
import com.lullaby.cardstudy.dto.CardResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardSetService cardSetService;

    public List<CardResponse> getCards(Long cardSetId) {
        CardSet cardSet = cardSetService.findCardSetEntity(cardSetId)
                .orElseThrow(() -> new NotFoundException("카드 셋을 찾을 수 없습니다."));

        return cardRepository.findByCardSet(cardSet)
                .stream().map(CardResponse::new)
                .toList();
    }

    public CardResponse addCard(AddCardCommand command) {
        CardSet cardSet = cardSetService.findCardSetEntity(command.cardSetId())
                .orElseThrow(() -> new NotFoundException("카드 셋을 찾을 수 없습니다."));

        Card card = new Card(cardSet, command.front(), command.back());
        cardRepository.save(card);
        cardSet.addCard();
        return new CardResponse(card);
    }

    public void deleteCard(Long id) {
        Card card = findCardEntity(id);
        card.getCardSet().deleteCard();
        cardRepository.delete(card);
    }

    public Card findCardEntity(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다."));
    }

    public void updateCard(Long id, UpdateCardCommand command) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다."));
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
