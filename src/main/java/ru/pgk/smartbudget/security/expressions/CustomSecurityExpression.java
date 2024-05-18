package ru.pgk.smartbudget.security.expressions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;
import ru.pgk.smartbudget.features.budget.services.BudgetService;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;
import ru.pgk.smartbudget.features.goal.services.GoalService;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;

@Service
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final BudgetService budgetService;
    private final GoalService goalService;
    private final ExpenseCategoryService expenseCategoryService;
    private final TransactionService transactionService;

    public Boolean canAccessBudget(Long userId, Long budgetId) {
        BudgetEntity budget = budgetService.getById(budgetId);
        return budget.getUser().getId().equals(userId);
    }

    public Boolean canAccessExpenseCategory(Long userId, Integer categoryId) {
        ExpenseCategoryEntity category = expenseCategoryService.getById(categoryId);
        return category.getUser() != null && category.getUser().getId().equals(userId);
    }

    public Boolean canAccessGoal(Long userId, Long goalId) {
        GoalEntity goal = goalService.getById(goalId);
        return goal.getUser().getId().equals(userId);
    }

    public Boolean canAccessTransaction(Long userId, Long transactionId) {
        TransactionEntity transaction = transactionService.getById(transactionId);
        return transaction.getUser().getId().equals(userId);
    }
}
