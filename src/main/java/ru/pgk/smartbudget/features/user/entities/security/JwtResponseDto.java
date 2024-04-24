package ru.pgk.smartbudget.features.user.entities.security;

import lombok.Data;

@Data
public class JwtResponseDto {

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
