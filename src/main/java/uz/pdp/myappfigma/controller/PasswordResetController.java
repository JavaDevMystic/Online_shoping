package uz.pdp.myappfigma.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.entity.AuthUser;
import uz.pdp.myappfigma.service.EmailService;
import uz.pdp.myappfigma.service.impl.UserCreateService;

import java.util.Optional;

@RestController
@RequestMapping("/auth/v1/password")
public class PasswordResetController {

    private final UserCreateService userCreateService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetController(UserCreateService userCreateService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userCreateService = userCreateService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/send/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<String> sendPassword(@Valid @RequestParam Long id) {

        Optional<AuthUser> currentUser = userCreateService.getById(id);
        String email = currentUser.get().getEmail();
        String code = emailService.sendVerificationEmail(email);
        return new BaseResponse<>(code);
    }

    @PostMapping("/password/reset/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<String> resetPassword(
            @PathVariable Long id,
            @RequestParam String password,
            @RequestParam String code) {

        AuthUser currentUser = userCreateService.getById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        if (!emailService.isCodeValid(code)) {
            return new BaseResponse<>("Verification code is invalid or expired");
        }

        String encodedPassword = userCreateService.getPasswordEncoder().encode(password);
        currentUser.setPassword(passwordEncoder.encode(encodedPassword));

        userCreateService.updateUser(currentUser);

        return new BaseResponse<>("Password has been reset successfully");
    }
}
