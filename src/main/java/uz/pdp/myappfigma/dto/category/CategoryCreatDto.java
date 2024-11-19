package uz.pdp.myappfigma.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uz.pdp.myappfigma.enums.Gender;

@Schema(description = "Kategorya yaratish uchun dto")
public record CategoryCreatDto(
        @Schema(description = "Kategoriya nomi", example = "Sport kiyimlari", required = true)
        @NotBlank(message = "Name maydoni bo'sh bo'lmasligi kerak!")
        String name,

        @Schema(description = "Gender (Jins)", example = "MALE", required = true)
        @NotNull(message = "Gender bo'sh bo'lmasligi kerak!")
        Gender gender,

        @Schema(description = "Bolalar kategoriyasi ID", example = "0", required = true)
        @NotNull(message = "Child ID bo'sh bo'lmasligi kerak!")
        Long childId
) {
}
