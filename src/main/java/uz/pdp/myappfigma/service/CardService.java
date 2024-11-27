package uz.pdp.myappfigma.service;

import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.card.CardCreateDto;
import uz.pdp.myappfigma.entity.Card;
import uz.pdp.myappfigma.generic.CardMapper;
import uz.pdp.myappfigma.repository.CardRepository;
import uz.pdp.myappfigma.utils.PendingPurchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final SessionUser sessionUser;

    private final Map<Long, PendingPurchase> pendingPurchases = new HashMap<>();

    public CardService(CardRepository cardRepository, CardMapper cardMapper, SessionUser sessionUser) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.sessionUser = sessionUser;
    }

    public Long create(CardCreateDto dto) {

        if (dto.cardNumber() == null || dto.cardNumber().length() < 16) {
            throw new IllegalArgumentException("Karta raqam 16 talik kalla bormi ??? ");
        }

        Long userId = sessionUser.getId();

        Card entity = cardMapper.toEntity(dto);
        entity.setUserId(userId);
        cardRepository.save(entity);
        return entity.getId();
    }

    public void validatetCard(String cardNumber, String validNumber, Long amount) {

        Long userId = sessionUser.getId();
        List<Card> cards = cardRepository.findByUserId(userId);

        if (cards.isEmpty()) {
            throw new IllegalArgumentException("Bu userga tegishli karta mavjud emas?");
        }

        Card matchingCard = cards.stream()
                .filter(card -> card.getCardNumber().equals(cardNumber) && card.getValidNumber().equals(validNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Karta ma'lumoti noto‘g‘ri"));

        if (matchingCard.getBalance() < amount) {
            throw new IllegalArgumentException("Karta raqamingizda hisob yetarli emas???");
        }
        pendingPurchases.put(userId, new PendingPurchase(matchingCard, amount));
    }


    public void processPayment(String code) {
        Long userId = sessionUser.getId();
        PendingPurchase pendingPurchase = pendingPurchases.remove(userId);

        if (pendingPurchase == null) {
            throw new IllegalArgumentException("Purchase not initiated for this user.");
        }

        Card card = pendingPurchase.getCard();
        Long amount = pendingPurchase.getAmount();

        card.setBalance(card.getBalance() - amount);
        cardRepository.save(card);
    }

    public Boolean deleteCard(Long id) {

        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
            return true;
        }
        return false;
    }

}