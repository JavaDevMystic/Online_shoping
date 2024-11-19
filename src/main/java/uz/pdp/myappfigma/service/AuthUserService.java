package uz.pdp.myappfigma.service;



import uz.pdp.myappfigma.dto.auth.AuthUserCreateDto;
import uz.pdp.myappfigma.dto.auth.AuthUserUpdateDto;
import uz.pdp.myappfigma.dto.auth.GenerateTokenRequest;
import uz.pdp.myappfigma.dto.auth.RefreshTokenRequest;
import uz.pdp.myappfigma.dto.auth.TokenResponse;
import uz.pdp.myappfigma.dto.auth.UserSessionData;
import uz.pdp.myappfigma.entity.AuthUser;

import java.util.Optional;

public interface AuthUserService {

    TokenResponse generateAccessToken(GenerateTokenRequest dto);

    Long createUser( AuthUserCreateDto dto);

    TokenResponse refreshToken(RefreshTokenRequest dto);

    Long update(Long id, AuthUserUpdateDto dto);


    UserSessionData getMe();

    Optional<AuthUser> getById(Long id);
}
