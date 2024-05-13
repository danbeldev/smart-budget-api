package ru.pgk.smartbudget.features.goal.services;

import org.springframework.data.domain.Page;
import ru.pgk.smartbudget.features.goal.dto.params.CreateOrUpdateGoalParams;
import ru.pgk.smartbudget.features.goal.dto.params.GetGoalsParams;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;

public interface GoalService {

    GoalEntity getById(Long id);

    Page<GoalEntity> getAll(GetGoalsParams params);

    GoalEntity create(Long userId, CreateOrUpdateGoalParams params);

    GoalEntity update(Long id, CreateOrUpdateGoalParams params);

    void updateCurrentAmount(Long id, Double amount);

    void deleteById(Long id);
}
