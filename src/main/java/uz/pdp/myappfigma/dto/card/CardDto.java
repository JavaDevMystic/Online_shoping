package uz.pdp.myappfigma.dto.card;

import lombok.Value;
import uz.pdp.myappfigma.enums.CardName;

import java.io.Serializable;

@Value
public class CardDto implements Serializable {
    Long id;
    Long userId;
    CardName cardName;
    String cardNumber;
    String validNumber;
    Long balance;
}

