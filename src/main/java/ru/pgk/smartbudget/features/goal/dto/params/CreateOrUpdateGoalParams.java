package ru.pgk.smartbudget.features.goal.dto.params;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class CreateOrUpdateGoalParams {

    @NotNull(message = "name must be not null")
    @Length(max = 48, message = "name must be smaller than 48 symbols")
    private String name;

    @NotNull(message = "targetAmount must be not null")
    @DecimalMin(value = "0.1", message = "targetAmount must be greater than or equal to 0.1")
    private Double targetAmount;

    private Double currentAmount = 0.0;
    private LocalDate deadline;
    private Boolean isAchieved = true;
}
