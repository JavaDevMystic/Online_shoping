package uz.pdp.myappfigma.service.impl;

import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.configuration.security.JwtTokenUtil;
import uz.pdp.myappfigma.configuration.security.UserSession;
import uz.pdp.myappfigma.dto.auth.AuthUserCreateDto;
import uz.pdp.myappfigma.dto.auth.AuthUserUpdateDto;
import uz.pdp.myappfigma.dto.auth.GenerateTokenRequest;
import uz.pdp.myappfigma.dto.auth.RefreshTokenRequest;
import uz.pdp.myappfigma.dto.auth.TokenResponse;
import uz.pdp.myappfigma.dto.auth.UserSessionData;
import uz.pdp.myappfigma.entity.AuthUser;
import uz.pdp.myappfigma.enums.JwtTokenType;
import uz.pdp.myappfigma.generic.AuthUserMapper;
import uz.pdp.myappfigma.repository.UserCreatRepository;
import uz.pdp.myappfigma.service.AuthUserService;

import java.util.Map;
import java.util.Optional;

@Service
public class UserCreateService implements AuthUserService {

    private final AuthUserMapper authUserMapper;
    private final UserCreatRepository userCreatRepository;
    private final PasswordEncoder bcryptPasswordEncoder;
    private final UserSession userSession;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserCreateService(AuthUserMapper authUserMapper, UserCreatRepository userCreatRepository, PasswordEncoder bcryptPasswordEncoder, UserSession userSession, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authUserMapper = authUserMapper;
        this.userCreatRepository = userCreatRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userSession = userSession;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public TokenResponse generateAccessToken(GenerateTokenRequest dto) {
        String username = dto.username();
        String password = dto.password();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        var accessTokenClaims = Map.<String, Object>of("token", JwtTokenType.ACCESS);
        var refreshTokenClaims = Map.<String, Object>of("token", JwtTokenType.REFRESH);
        String accessToken = jwtTokenUtil.generateAccessToken(username, accessTokenClaims);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username, refreshTokenClaims);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public Long createUser(AuthUserCreateDto dto) {

        Optional<AuthUser> user = userCreatRepository.checkByUsernameAndPassword(dto.username(), dto.password());
        if (user.isPresent()){
            return -1L;
        }
        AuthUser authUser = new AuthUser();
        authUser.setEmail(dto.email());
        authUser.setUsername(dto.username());
        authUser.setPassword(bcryptPasswordEncoder.encode(dto.password()));
        authUser.setFirsName(dto.firstName());
        authUser.setLastName(dto.lastName());
        authUser.setIsActive(true);
        userCreatRepository.save(authUser);
        return authUser.getId();
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest dto) {
        return null;
    }

    @Override
    public Long update(Long id, AuthUserUpdateDto dto) {

        Optional<AuthUser> user = userCreatRepository.findById(id);
        AuthUser authUser = user.get();
        if (user.isPresent()){
            return -1L;
        }
        authUserMapper.partialUpdate(dto, authUser);
        userCreatRepository.save(authUser);
        return authUser.getId();

    }

    public Boolean delete(Long id) {
        if (userCreatRepository.existsById(id)){
            userCreatRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserSessionData getMe() {
        return userSession.requireUserData();
    }

    @Override
    public Optional<AuthUser> getById(Long id) {
        AuthUser authUser = userCreatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found +" + id));
        return Optional.of(authUser);
    }

    public PasswordEncoder getPasswordEncoder() {
        return bcryptPasswordEncoder;
    }

    public void updateUser(AuthUser authUser) {
        userCreatRepository.save(authUser);
    }

}

