package ru.pgk.smartbudget.features.goal.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.goal.dto.GoalDto;
import ru.pgk.smartbudget.features.goal.dto.params.CreateOrUpdateGoalParams;
import ru.pgk.smartbudget.features.goal.dto.params.GetGoalsParams;
import ru.pgk.smartbudget.features.goal.dto.params.GoalOrderByType;
import ru.pgk.smartbudget.features.goal.mappers.GoalMapper;
import ru.pgk.smartbudget.features.goal.services.GoalService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

import java.time.LocalDate;

@RestController
@RequestMapping("goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    private final GoalMapper goalMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<GoalDto> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isAchieved,
            @RequestParam(required = false) LocalDate deadlineStartDate,
            @RequestParam(required = false) LocalDate deadlineEndDate,
            @RequestParam(required = false) GoalOrderByType orderByType,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        GetGoalsParams params = new GetGoalsParams();
        params.setSearch(search);
        params.setUserId(jwtEntity.getUserId());
        params.setIsAchieved(isAchieved);
        params.setDeadlineStartDate(deadlineStartDate);
        params.setDeadlineEndDate(deadlineEndDate);
        params.setOrderByType(orderByType);
        params.setOffset(offset);
        params.setLimit(limit);

        return PageDto.fromPage(goalService.getAll(params).map(goalMapper::toDto));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    private GoalDto create(
            @RequestBody CreateOrUpdateGoalParams params,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return goalMapper.toDto(goalService.create(jwtEntity.getUserId(), params));
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private GoalDto update(
            @PathVariable Long id,
            @RequestBody CreateOrUpdateGoalParams params
    ) {
        return goalMapper.toDto(goalService.update(id, params));
    }

    @PatchMapping("{id}/current-amount")
    @SecurityRequirement(name = "bearerAuth")
    private void updateCurrentAmount(
            @PathVariable Long id,
            @RequestParam Double amount
    ) {
        goalService.updateCurrentAmount(id, amount);
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(
            @PathVariable Long id
    ) {
        goalService.deleteById(id);
    }
}
