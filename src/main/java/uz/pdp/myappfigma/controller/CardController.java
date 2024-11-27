package uz.pdp.myappfigma.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.card.CardCreateDto;
import uz.pdp.myappfigma.service.CardService;
import uz.pdp.myappfigma.service.EmailService;
import uz.pdp.myappfigma.service.impl.UserCreateService;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;
    private final SessionUser sessionUser;

    public CardController(CardService cardService, SessionUser sessionUser) {
        this.cardService = cardService;
        this.sessionUser = sessionUser;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Long> create(@Valid @RequestBody CardCreateDto dto) {
        Long newId = cardService.create(dto);
        return new BaseResponse<>(newId);
    }

    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> delete() {
        Long userId = sessionUser.getId();
        Boolean delete = cardService.deleteCard(userId);
        return new BaseResponse<>(delete);
    }
}
