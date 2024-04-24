package ru.pgk.smartbudget.features.user.services.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.BadRequestException;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.entities.security.JwtRequestDto;
import ru.pgk.smartbudget.features.user.entities.security.JwtResponseDto;
import ru.pgk.smartbudget.features.user.services.UserService;
import ru.pgk.smartbudget.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDto login(JwtRequestDto dto) {
        UserEntity user = userService.getByEmail(dto.email());

        if(!passwordEncoder.matches(dto.password(), user.getPasswordHash()))
            throw new BadRequestException("Password invalid");

        return userToJwtEntity(
                user,
                jwtTokenProvider.createAccessToken(user),
                jwtTokenProvider.createRefreshToken(user)
        );
    }

    @Override
    @Transactional
    public JwtResponseDto registration(JwtRequestDto dto) {
        UserEntity user = new UserEntity();

        user.setEmail(dto.email());
        user.setPasswordHash(dto.password());
        user = userService.add(user);

        return userToJwtEntity(
                user,
                jwtTokenProvider.createAccessToken(user),
                jwtTokenProvider.createRefreshToken(user)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDto refresh(String refreshToken) {
        if(jwtTokenProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            UserEntity user = userService.getByEmail(email);
            return userToJwtEntity(user, jwtTokenProvider.createAccessToken(user), refreshToken);
        }
        throw new BadRequestException("Token invalid");
    }

    private JwtResponseDto userToJwtEntity(UserEntity user, String accessToken, String refreshToken) {
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setUserId(user.getId());
        jwtResponseDto.setAccessToken(accessToken);
        jwtResponseDto.setRefreshToken(refreshToken);
        return jwtResponseDto;
    }
}
