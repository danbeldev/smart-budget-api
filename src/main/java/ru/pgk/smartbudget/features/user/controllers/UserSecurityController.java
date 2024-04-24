package ru.pgk.smartbudget.features.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.features.user.entities.security.JwtRequestDto;
import ru.pgk.smartbudget.features.user.entities.security.JwtResponseDto;
import ru.pgk.smartbudget.features.user.services.security.UserSecurityService;

@RestController
@RequestMapping("user/security")
@RequiredArgsConstructor
public class UserSecurityController {

    private final UserSecurityService userSecurityService;

    @PostMapping("login")
    private JwtResponseDto login(
            @RequestBody JwtRequestDto request
    ) {
        return userSecurityService.login(request);
    }

    @PostMapping("registration")
    private JwtResponseDto registration(
            @RequestBody JwtRequestDto request
    ) {
        return userSecurityService.registration(request);
    }

    @PostMapping("refresh")
    private JwtResponseDto refresh(
            @RequestHeader("refresh-token") String refreshToken
    ) {
        return userSecurityService.refresh(refreshToken);
    }
}
