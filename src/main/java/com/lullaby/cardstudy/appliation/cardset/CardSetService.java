package com.lullaby.cardstudy.appliation.cardset;

import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import com.lullaby.cardstudy.appliation.cardset.dto.UpdateCardSetCommand;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.domain.cardset.CardSet;
import com.lullaby.cardstudy.domain.cardset.CardSetRepository;
import com.lullaby.cardstudy.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CardSetService {

    private final CardSetRepository cardSetRepository;
    private final MemberService memberService;

    public List<CardSetResponse> getCardSets(Long userId) {
        return cardSetRepository.findAllByOwnerId(userId)
                .stream().map(CardSetResponse::new)
                .toList();
    }
    public CardSetResponse addCardSet(Long userId, AddCardSetCommand command) {
        Member member = memberService.findEntityOrElseThrow(userId);
        CardSet cardSet = new CardSet(
                command.type()
                , command.name()
                , command.description()
                , member
        );
        return new CardSetResponse(cardSetRepository.save(cardSet));
    }

    public void deleteCardSet(Long userId, Long id) {
        CardSet cardSet = findCardSetEntityOrElseThrow(id, userId);
        cardSetRepository.delete(cardSet);
    }

    public CardSetResponse updateCardSet(Long userId, Long id, UpdateCardSetCommand command) {
        CardSet cardSet = findCardSetEntityOrElseThrow(id, userId);
        if (command.name() != null) {
            cardSet.setName(command.name());
        }
        if (command.description() != null) {
            cardSet.setDescription(command.description());
        }
        return new CardSetResponse(cardSetRepository.save(cardSet));
    }

    public CardSet findCardSetEntityOrElseThrow(Long id, Long ownerId) {
        return cardSetRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "카드 셋을 찾을 수 없습니다."));
    }

    public CardSet findCardWithinCardSet(Long cardSetId, Long cardId, Long ownerId) {
        return cardSetRepository.findCard(cardSetId, cardId, ownerId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "카드 셋을 찾을 수 없습니다."));
    }

}
