package ru.pgk.smartbudget.features.goal.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.goal.dto.GoalDto;
import ru.pgk.smartbudget.features.goal.dto.params.CreateOrUpdateGoalParams;
import ru.pgk.smartbudget.features.goal.mappers.GoalMapper;
import ru.pgk.smartbudget.features.goal.services.GoalService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@RestController
@RequestMapping("goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    private final GoalMapper goalMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<GoalDto> getAll(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return PageDto.fromPage(
                goalService.getAllByUserId(jwtEntity.getUserId(), offset, limit)
                        .map(goalMapper::toDto)
        );
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
