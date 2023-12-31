package com.lullaby.cardstudy.appliation.card;

import com.lullaby.cardstudy.appliation.card.dto.AddCardCommand;
import com.lullaby.cardstudy.appliation.card.dto.UpdateCardCommand;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.common.exception.NotFoundException;
import com.lullaby.cardstudy.domain.Card;
import com.lullaby.cardstudy.domain.CardRepository;
import com.lullaby.cardstudy.domain.CardSet;
import com.lullaby.cardstudy.appliation.card.dto.CardResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        return cardRepository.findByCardSetOrderByIdDesc(cardSet)
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

    public CardResponse updateCard(Long id, UpdateCardCommand command) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다."));
        if (command.front() != null) {
            card.setFront(command.front());
        }
        if (command.back() != null) {
            card.setBack(command.back());
        }
        card.clearMemorizationLevel();
        return new CardResponse(card);
    }

    public List<CardResponse> addCardByFile(Long cardSetId, String textFileContent) {
        List<CardResponse> cardResponses = new ArrayList<>();

        for (String text : textFileContent.split("@@")) {

            if (text.startsWith("\n")) {
                text = text.substring(1);
            }
            String front = StringUtils.substringBefore(text, "\n").trim();
            String back = StringUtils.substringAfter(text, "\n").trim();
            CardResponse cardResponse = addCard(new AddCardCommand(cardSetId, front, back));
            cardResponses.add(cardResponse);
        }

        return cardResponses;
    }

}
