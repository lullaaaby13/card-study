package com.lullaby.cardstudy.appliation.cardset;

import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.UpdateCardSetCommand;
import com.lullaby.cardstudy.domain.CardSet;
import com.lullaby.cardstudy.domain.CardSetRepository;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
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
    public CardSetResponse addCardSet(AddCardSetCommand command) {
        CardSet cardSet = new CardSet(command.name(), command.description());
        return new CardSetResponse(cardSetRepository.save(cardSet));
    }

    public void deleteCardSet(Long id) {
        cardSetRepository.deleteById(id);
    }

    public CardSetResponse updateCardSet(Long id, UpdateCardSetCommand command) {
        CardSet cardSet = cardSetRepository.findById(id).orElseThrow();
        cardSet.setName(command.name());
        cardSet.setDescription(command.description());
        return new CardSetResponse(cardSetRepository.save(cardSet));
    }

    public Optional<CardSet> findCardSetEntity(Long id) {
        return cardSetRepository.findById(id);
    }

}
