package uz.pdp.myappfigma.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.card.CardCreateDto;
import uz.pdp.myappfigma.entity.AuthUser;
import uz.pdp.myappfigma.service.CardService;
import uz.pdp.myappfigma.service.EmailService;
import uz.pdp.myappfigma.service.impl.UserCreateService;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;
    private final SessionUser sessionUser;
    private final UserCreateService userCreateService;
    private final EmailService emailService;

    public CardController(CardService cardService, SessionUser sessionUser, UserCreateService userCreateService, EmailService emailService) {
        this.cardService = cardService;
        this.sessionUser = sessionUser;
        this.userCreateService = userCreateService;
        this.emailService = emailService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Long> create(@Valid @RequestBody CardCreateDto dto) {
        Long newId = cardService.create(dto);
        return new BaseResponse<>(newId);
    }

    @PostMapping("/purchase")
    public BaseResponse<String> initiatePurchase(
            @RequestParam String cardNumber,
            @RequestParam String validNumber,
            @RequestParam Long amount) {

        cardService.validatetCard(cardNumber, validNumber, amount);

        Long userId = sessionUser.getId();
        AuthUser currentUser = userCreateService.getById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        String email = currentUser.getEmail();
        String code = emailService.sendVerificationEmail(email);

        return new BaseResponse<>(code);
    }

    @PostMapping("/confirm")
    public BaseResponse<String> confirmPurchase(
            @RequestParam String code) {

        if (!emailService.isCodeValid(code)) {
            return new BaseResponse<>("Verification code is invalid or expired");
        }

        cardService.processPayment(code);

        return new BaseResponse<>("Payment successful");
    }
}
