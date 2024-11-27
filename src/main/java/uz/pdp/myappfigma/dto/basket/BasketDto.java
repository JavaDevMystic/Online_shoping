package uz.pdp.myappfigma.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.myappfigma.dto.product.ProductDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {

    private Long id;
    private Long authUserId;
    private ProductDto productDto;
    private Boolean isCanselOrder;
    private Integer quantity;
    private Long totalAmount;

}
