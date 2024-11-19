package uz.pdp.myappfigma.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketUpdateDto {

    private Long  authUserId;
    private Long productId;
    private Boolean canselOrder;
}
