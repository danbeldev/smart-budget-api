package ru.pgk.smartbudget.features.goal.dto.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetGoalsParams {

    private String search;
    private Long userId;
    private Boolean isAchieved;
    private LocalDate deadlineStartDate;
    private LocalDate deadlineEndDate;

    private GoalOrderByType orderByType;

    private Integer offset = 0;
    private Integer limit = 20;
}
