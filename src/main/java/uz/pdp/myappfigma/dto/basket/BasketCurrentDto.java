package uz.pdp.myappfigma.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.myappfigma.entity.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BasketCurrentDto {

    private List<Product> products;
    private Long total;
}
