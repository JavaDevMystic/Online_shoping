package uz.pdp.myappfigma.dto.product;



import java.io.Serializable;

public record ProductCreateDto(String name, Double price, Integer discount,
                               Long categoryId,Long count) implements Serializable {
}



