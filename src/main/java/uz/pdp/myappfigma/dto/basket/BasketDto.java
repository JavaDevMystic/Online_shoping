package uz.pdp.myappfigma.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.myappfigma.entity.Category;
import uz.pdp.myappfigma.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {

    private Long id;
    private Long categoryId;
    private Long productId;
}
