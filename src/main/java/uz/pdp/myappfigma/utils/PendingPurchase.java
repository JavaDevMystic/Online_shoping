package uz.pdp.myappfigma.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.myappfigma.entity.Card;

@Getter
@Setter
@AllArgsConstructor
public class PendingPurchase {
    private Card card;
    private Long amount;

}
