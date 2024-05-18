package ru.pgk.smartbudget.features.user.entities.security;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record JwtRequestDto(
        @NotNull(message = "email must be not null")
        @Length(max = 48, message = "email must be smaller than 48 symbols")
        String email,
        @NotNull(message = "password must be not null")
        @Length(max = 48, message = "password must be smaller than 48 symbols")
        String password
) {}
