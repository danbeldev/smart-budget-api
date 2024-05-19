package ru.pgk.smartbudget.features.user.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.pgk.smartbudget.common.exceptions.BadRequestException;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.entities.security.JwtRequestDto;
import ru.pgk.smartbudget.features.user.entities.security.JwtResponseDto;
import ru.pgk.smartbudget.features.user.services.UserService;
import ru.pgk.smartbudget.security.jwt.JwtTokenProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceImplTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserSecurityServiceImpl userSecurityService;

    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_PASSWORD = "password";

    @BeforeEach
    void setUp() {
        // Mocking behavior for userService and jwtTokenProvider methods
        // for each test case
    }

    @Test
    void login_ValidCredentials_ReturnsJwtResponseDto() {
        // Arrange
        JwtRequestDto requestDto = new JwtRequestDto(VALID_EMAIL, VALID_PASSWORD);
        UserEntity user = new UserEntity();
        user.setEmail(VALID_EMAIL);
        user.setPasswordHash(VALID_PASSWORD);
        when(userService.getByEmail(VALID_EMAIL)).thenReturn(user);
        when(passwordEncoder.matches(VALID_PASSWORD, user.getPasswordHash())).thenReturn(true);
        when(jwtTokenProvider.createAccessToken(user)).thenReturn("access_token");
        when(jwtTokenProvider.createRefreshToken(user)).thenReturn("refresh_token");

        // Act
        JwtResponseDto responseDto = userSecurityService.login(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(user.getId(), responseDto.getUserId());
        assertNotNull(responseDto.getAccessToken());
        assertNotNull(responseDto.getRefreshToken());
    }

    @Test
    void login_InvalidPassword_ThrowsBadRequestException() {
        // Arrange
        JwtRequestDto requestDto = new JwtRequestDto(VALID_EMAIL, "invalid_password");
        UserEntity user = new UserEntity();
        user.setEmail(VALID_EMAIL);
        user.setPasswordHash(VALID_PASSWORD);
        when(userService.getByEmail(VALID_EMAIL)).thenReturn(user);
        when(passwordEncoder.matches("invalid_password", user.getPasswordHash())).thenReturn(false);

        // Assert
        assertThrows(BadRequestException.class, () -> userSecurityService.login(requestDto));
    }

    @Test
    void registration_ValidDto_ReturnsJwtResponseDto() {
        // Arrange
        JwtRequestDto requestDto = new JwtRequestDto(VALID_EMAIL, VALID_PASSWORD);
        UserEntity user = new UserEntity();
        user.setEmail(VALID_EMAIL);
        user.setPasswordHash(VALID_PASSWORD);
        when(userService.add(any())).thenReturn(user);
        when(jwtTokenProvider.createAccessToken(user)).thenReturn("access_token");
        when(jwtTokenProvider.createRefreshToken(user)).thenReturn("refresh_token");

        // Act
        JwtResponseDto responseDto = userSecurityService.registration(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(user.getId(), responseDto.getUserId());
        assertNotNull(responseDto.getAccessToken());
        assertNotNull(responseDto.getRefreshToken());
    }

    @Test
    void refresh_ValidRefreshToken_ReturnsJwtResponseDto() {
        // Arrange
        String refreshToken = "valid_refresh_token";
        Claims claims = Jwts.claims().setSubject(VALID_EMAIL);
        when(jwtTokenProvider.validateRefreshToken(refreshToken)).thenReturn(true);
        when(jwtTokenProvider.getRefreshClaims(refreshToken)).thenReturn(claims);
        UserEntity user = new UserEntity();
        user.setEmail(VALID_EMAIL);
        when(userService.getByEmail(VALID_EMAIL)).thenReturn(user);
        when(jwtTokenProvider.createAccessToken(user)).thenReturn("access_token");

        // Act
        JwtResponseDto responseDto = userSecurityService.refresh(refreshToken);

        // Assert
        assertNotNull(responseDto);
        assertEquals(user.getId(), responseDto.getUserId());
        assertNotNull(responseDto.getAccessToken());
        assertEquals(refreshToken, responseDto.getRefreshToken());
    }
}
