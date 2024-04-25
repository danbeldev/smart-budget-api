package ru.pgk.smartbudget.features.goal.dto;

import java.time.LocalDate;

public record GoalDto(
        Integer id,
        String name,
        Double targetAmount,
        Double currentAmount,
        LocalDate deadline,
        Boolean isAchieved
) {}
