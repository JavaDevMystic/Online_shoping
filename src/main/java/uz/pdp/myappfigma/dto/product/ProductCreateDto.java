package uz.pdp.myappfigma.dto.product;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ProductCreateDto(String name,Double price,Integer discount, Long categoryId, Integer count,Long brandId) implements Serializable {
}


