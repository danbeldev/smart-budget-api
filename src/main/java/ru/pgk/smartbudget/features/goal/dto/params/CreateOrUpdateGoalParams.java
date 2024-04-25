package ru.pgk.smartbudget.features.goal.dto.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOrUpdateGoalParams {

    private String name;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate deadline = null;
    private Boolean isAchieved = true;
}
