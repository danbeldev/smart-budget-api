package ru.pgk.smartbudget.features.user.entities.security;

public record JwtRequestDto(
        String email,
        String password
) {}
