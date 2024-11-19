package uz.pdp.myappfigma.dto.auth;

public record AuthUserUpdateDto(String email,
                                String username,
                                String password,
                                String firstName,
                                String lastName) {
}
