package uz.pdp.myappfigma.dto.product;



import java.io.Serializable;


public record ProductUpdateDto(String name, Double price, Integer discount, Integer count) implements Serializable {
}