package uz.pdp.myappfigma.dto.product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;


import java.io.Serializable;


@AllArgsConstructor
@Value
public class ProductDto implements Serializable {
    String name;
    Double price;
    Integer quantity;

}