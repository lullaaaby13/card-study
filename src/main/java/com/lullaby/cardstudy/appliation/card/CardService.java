package com.lullaby.cardstudy.appliation.card;

import com.lullaby.cardstudy.appliation.card.dto.AddCardCommand;
import com.lullaby.cardstudy.appliation.card.dto.CardResponse;
import com.lullaby.cardstudy.appliation.card.dto.UpdateCardCommand;
import com.lullaby.cardstudy.appliation.cardset.CardSetService;
import com.lullaby.cardstudy.appliation.member.MemberService;
import com.lullaby.cardstudy.common.exception.NotFoundException;
import com.lullaby.cardstudy.domain.Card;
import com.lullaby.cardstudy.domain.CardRepository;
import com.lullaby.cardstudy.domain.CardSet;
import com.lullaby.cardstudy.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardSetService cardSetService;
    private final MemberService memberService;

    public List<CardResponse> getCards(Long userId, Long cardSetId) {
        CardSet cardSet = cardSetService.findCardSetEntityOrElseThrow(cardSetId, userId);

        return cardRepository.findByCardSetOrderByIdDesc(cardSet)
                .stream().map(CardResponse::new)
                .toList();
    }

    public CardResponse addCard(Long userId, AddCardCommand command) {
        CardSet cardSet = cardSetService.findCardSetEntityOrElseThrow(command.cardSetId(), userId);
        Member member = memberService.findMemberEntityOrElseThrow(userId);
        if (!cardSet.isOwnedBy(member)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "카드 소유자만 카드를 추가할 수 있습니다.");
        }

        Card card = new Card(cardSet, command.front(), command.back());
        cardRepository.save(card);
        cardSet.addCard();
        return new CardResponse(card);
    }

    public void deleteCard(Long userId, Long id) {
        Card card = findCardEntityOrElseThrow(id);
        CardSet cardSet = card.getCardSet();
        Member member = memberService.findMemberEntityOrElseThrow(userId);
        if (!cardSet.isOwnedBy(member)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "카드 소유자만 카드를 삭제할 수 있습니다.");
        }
        card.getCardSet().deleteCard();
        cardRepository.delete(card);
    }

    public Card findCardEntityOrElseThrow(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다."));
    }

    public CardResponse updateCard(Long userId, Long id, UpdateCardCommand command) {
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

    public List<CardResponse> addCardByFile(Long userId, Long cardSetId, String textFileContent) {
        List<CardResponse> cardResponses = new ArrayList<>();

        for (String text : textFileContent.split("@@")) {

            if (text.startsWith("\n")) {
                text = text.substring(1);
            }
            String front = StringUtils.substringBefore(text, "\n").trim();
            String back = StringUtils.substringAfter(text, "\n").trim();
            CardResponse cardResponse = addCard(userId, new AddCardCommand(cardSetId, front, back));
            cardResponses.add(cardResponse);
        }

        return cardResponses;
    }

    private void validateCardOwner(Long userId, Card card) {
        CardSet cardSet = card.getCardSet();
        Member member = memberService.findMemberEntityOrElseThrow(userId);
        if (!cardSet.isOwnedBy(member)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "카드 소유자만 카드를 추가할 수 있습니다.");
        }
    }

}
