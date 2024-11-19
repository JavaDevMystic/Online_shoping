package uz.pdp.myappfigma.dto.auth;

public record AuthUserCreateDto(
        String email,
        String username,
        String password,
        String firstName,
        String lastName
) {
}
