package ru.pgk.smartbudget.features.user.services.security;

import ru.pgk.smartbudget.features.user.entities.security.JwtRequestDto;
import ru.pgk.smartbudget.features.user.entities.security.JwtResponseDto;

public interface UserSecurityService {

    JwtResponseDto login(JwtRequestDto dto);

    JwtResponseDto registration(JwtRequestDto dto);

    JwtResponseDto refresh(String refreshToken);
}
