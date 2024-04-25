package ru.pgk.smartbudget.features.budget.services;

import org.springframework.data.domain.Page;
import ru.pgk.smartbudget.features.budget.dto.params.CreateOrUpdateBudgetParams;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;

public interface BudgetService {

    BudgetEntity getById(Long id);

    Page<BudgetEntity> getAllByUserId(Long userId, Integer offset, Integer limit);

    BudgetEntity create(Long userId, CreateOrUpdateBudgetParams params);

    void update(Long id, CreateOrUpdateBudgetParams params);

    void deleteById(Long id);
}
