package uz.pdp.myappfigma.dto.auth;

import java.time.LocalDateTime;

public record AuthUserDto(String id,
                          String username,
                          String password,
                          String email,
                          String firstName,
                          String lastName,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt) {
}
