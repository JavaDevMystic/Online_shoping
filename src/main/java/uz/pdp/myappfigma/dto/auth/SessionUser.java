package uz.pdp.myappfigma.dto.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SessionUser {

    public CustomUserDetails user() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            throw new RuntimeException("Foydalanuvchi tizimga kirmagan!");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails user) {
            return user;
        }

        throw new RuntimeException("Foydalanuvchi ma'lumotlari topilmadi!");
    }

    public Long getId() {
        CustomUserDetails user = user();
        return user.getId();
    }
}
