package uz.pdp.myappfigma.entity;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class AuthUserAuditable {

    @CurrentTimestamp(event = EventType.INSERT, source = SourceType.VM)
    private LocalDateTime createdAt;

    @CurrentTimestamp(event = EventType.UPDATE, source = SourceType.VM)
    private LocalDateTime updatedAt;
}
