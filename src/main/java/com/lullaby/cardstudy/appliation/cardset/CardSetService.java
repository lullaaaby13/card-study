package com.lullaby.cardstudy.appliation.cardset;

import com.lullaby.cardstudy.appliation.cardset.dto.AddCardSetCommand;
import com.lullaby.cardstudy.appliation.cardset.dto.CardSetResponse;
import com.lullaby.cardstudy.appliation.cardset.dto.UpdateCardSetCommand;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.common.exception.NotFoundException;
import com.lullaby.cardstudy.domain.CardSet;
import com.lullaby.cardstudy.domain.CardSetRepository;
import com.lullaby.cardstudy.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Member member = memberService.findMemberEntityOrElseThrow(userId);
        CardSet cardSet = new CardSet(
                command.type()
                , command.name()
                , command.description()
                , member
        );
        return new CardSetResponse(cardSetRepository.save(cardSet));
    }

    public void deleteCardSet(Long userId, Long id) {
        memberService.findMemberEntityOrElseThrow(userId);
        cardSetRepository.deleteById(id);
    }

    public CardSetResponse updateCardSet(Long userId, Long id, UpdateCardSetCommand command) {
        CardSet cardSet = cardSetRepository.findById(id).orElseThrow();
        cardSet.setName(command.name());
        cardSet.setDescription(command.description());
        return new CardSetResponse(cardSetRepository.save(cardSet));
    }

    public CardSet findCardSetEntityOrElseThrow(Long id, Long ownerId) {
        return cardSetRepository.findByIdAndOwnerId(id, ownerId).orElseThrow(() -> new NotFoundException("카드 셋을 찾을 수 없습니다."));
    }

}
