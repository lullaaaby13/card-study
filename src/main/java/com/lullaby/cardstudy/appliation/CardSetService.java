package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.domain.CardSet;
import com.lullaby.cardstudy.domain.CardSetRepository;
import com.lullaby.cardstudy.dto.CardSetResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CardSetService {

    private final CardSetRepository cardSetRepository;

    public List<CardSetResponse> getCardSets() {
        return cardSetRepository.findAll()
                .stream().map(CardSetResponse::new)
                .toList();
    }
    public void addCardSet(AddCardSetCommand command) {
        CardSet cardSet = new CardSet(command.name(), command.description());
        cardSetRepository.save(cardSet);
    }

    public void deleteCardSet(Long id) {
        cardSetRepository.deleteById(id);
    }

    public void updateCardSet(Long id, UpdateCardSetCommand command) {
        CardSet cardSet = cardSetRepository.findById(id).orElseThrow();
        cardSet.setName(command.name());
        cardSet.setDescription(command.description());
        cardSetRepository.save(cardSet);
    }

    public Optional<CardSet> findCardSetEntity(Long id) {
        return cardSetRepository.findById(id);
    }

}
