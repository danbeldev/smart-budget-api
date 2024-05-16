package ru.pgk.smartbudget.features.user.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.smartbudget.features.user.dto.UserDto;
import ru.pgk.smartbudget.features.user.mappers.UserMapper;
import ru.pgk.smartbudget.features.user.services.UserService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("info")
    @SecurityRequirement(name = "bearerAuth")
    public UserDto getInfo(
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return userMapper.toDto(userService.getById(jwtEntity.getUserId()));
    }
}
