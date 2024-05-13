package ru.pgk.smartbudget.features.goal.dto.params;

import lombok.Getter;

@Getter
public enum GoalOrderByType {
    NAME_ASC("name", false),
    NAME_DESC("name", true),
    TARGET_AMOUNT_ASC("targetAmount", false),
    TARGET_AMOUNT_DESC("targetAmount", true),
    CURRENT_AMOUNT_ASC("currentAmount", false),
    CURRENT_AMOUNT_DESC("currentAmount", true),
    DEADLINE_ASC("deadline", false),
    DEADLINE_DESC("deadline", true);

    private final String fieldName;
    private final boolean descending;

    GoalOrderByType(String fieldName, Boolean descending) {
        this.fieldName = fieldName;
        this.descending = descending;
    }
}
