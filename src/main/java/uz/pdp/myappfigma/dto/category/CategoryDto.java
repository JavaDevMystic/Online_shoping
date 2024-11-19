package uz.pdp.myappfigma.dto.category;

import lombok.Value;
import uz.pdp.myappfigma.enums.Gender;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class CategoryDto implements Serializable {
        Long id;
        String name;
        Gender gender;
        Long childId;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        Long createdBy;
        Long updateBy;
}
