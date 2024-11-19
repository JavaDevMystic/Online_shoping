package uz.pdp.myappfigma.dto.card;

import uz.pdp.myappfigma.enums.CardName;

public record CardCreateDto(
        CardName cardName,
        String cardNumber,
        String validNumber
) {
}
