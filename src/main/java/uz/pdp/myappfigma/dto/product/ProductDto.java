package uz.pdp.myappfigma.dto.product;

import lombok.Value;


import java.io.Serializable;
import java.time.LocalDateTime;


@Value
public class ProductDto implements Serializable {
    String name;
    Double price;
    Integer count;


}