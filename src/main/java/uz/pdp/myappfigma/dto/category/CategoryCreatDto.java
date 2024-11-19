package uz.pdp.myappfigma.dto.category;

import uz.pdp.myappfigma.enums.Gender;

public record CategoryCreatDto(
        String name,
        Gender gender,
        Long childId
) {
}
