package ru.pgk.smartbudget.features.budget.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.budget.dto.params.CreateOrUpdateBudgetParams;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;
import ru.pgk.smartbudget.features.budget.repositories.BudgetRepository;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final UserService userService;
    private final ExpenseCategoryService expenseCategoryService;

    @Override
    @Transactional(readOnly = true)
    public BudgetEntity getById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BudgetEntity> getAllByUserId(Long userId, Integer offset, Integer limit) {
        return budgetRepository.findAllByUserId(
                userId, PageRequest.of(offset, limit)
        );
    }

    @Override
    @Transactional
    public BudgetEntity create(Long userId, CreateOrUpdateBudgetParams params) {
        UserEntity user = userService.getById(userId);
        BudgetEntity budget = new BudgetEntity();
        budget.setUser(user);
        setParams(budget, params);
        return budgetRepository.save(budget);
    }

    @Override
    @Transactional
    public void update(Long id, CreateOrUpdateBudgetParams params) {
        BudgetEntity budget = getById(id);
        setParams(budget, params);
        budgetRepository.save(budget);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        budgetRepository.deleteById(id);
    }

    private void setParams(BudgetEntity budget, CreateOrUpdateBudgetParams params) {
        ExpenseCategoryEntity category = expenseCategoryService.getById(params.getCategoryId());
        budget.setAmountLimit(params.getAmountLimit());
        budget.setStartDate(params.getStartDate());
        budget.setEndDate(params.getEndDate());
        budget.setCategory(category);
    }
}
