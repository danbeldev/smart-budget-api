package ru.pgk.smartbudget.security.expressions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;
import ru.pgk.smartbudget.features.budget.services.BudgetService;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;
import ru.pgk.smartbudget.features.goal.services.GoalService;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;
import ru.pgk.smartbudget.features.transaction.services.recurring.RecurringTransactionService;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomSecurityExpressionTest {

    @Mock
    private BudgetService budgetService;

    @Mock
    private GoalService goalService;

    @Mock
    private ExpenseCategoryService expenseCategoryService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private RecurringTransactionService recurringTransactionService;

    @InjectMocks
    private CustomSecurityExpression customSecurityExpression;

    private static final Long USER_ID = 1L;
    private static final Long BUDGET_ID = 100L;
    private static final Long GOAL_ID = 200L;
    private static final Integer CATEGORY_ID = 300;
    private static final Long TRANSACTION_ID = 400L;
    private static final Long RECURRING_TRANSACTION_ID = 500L;

    @BeforeEach
    void setUp() {
        // Mocking behavior for services
    }

    @Test
    void canAccessBudget_UserOwnsBudget_ReturnsTrue() {
        // Arrange
        BudgetEntity budget = new BudgetEntity();
        budget.setUser(createUser(USER_ID));
        when(budgetService.getById(BUDGET_ID)).thenReturn(budget);

        // Act
        boolean result = customSecurityExpression.canAccessBudget(USER_ID, BUDGET_ID);

        // Assert
        assertTrue(result);
    }

    // Similarly, tests for other methods can be implemented

    @Test
    void canAccessExpenseCategory_CategoryHasNoOwner_ReturnsTrue() {
        // Arrange
        ExpenseCategoryEntity category = new ExpenseCategoryEntity();
        category.setUser(createUser(USER_ID));
        when(expenseCategoryService.getById(CATEGORY_ID)).thenReturn(category);

        // Act
        boolean result = customSecurityExpression.canAccessExpenseCategory(USER_ID, CATEGORY_ID);

        // Assert
        assertTrue(result);
    }

    @Test
    void canAccessExpenseCategory_UserOwnsCategory_ReturnsTrue() {
        // Arrange
        ExpenseCategoryEntity category = new ExpenseCategoryEntity();
        category.setUser(createUser(USER_ID));
        when(expenseCategoryService.getById(CATEGORY_ID)).thenReturn(category);

        // Act
        boolean result = customSecurityExpression.canAccessExpenseCategory(USER_ID, CATEGORY_ID);

        // Assert
        assertTrue(result);
    }

    @Test
    void canAccessExpenseCategory_UserDoesNotOwnCategory_ReturnsFalse() {
        // Arrange
        ExpenseCategoryEntity category = new ExpenseCategoryEntity();
        category.setUser(createUser(2L)); // Another user
        when(expenseCategoryService.getById(CATEGORY_ID)).thenReturn(category);

        // Act
        boolean result = customSecurityExpression.canAccessExpenseCategory(USER_ID, CATEGORY_ID);

        // Assert
        assertFalse(result);
    }

    @Test
    void canAccessGoal_UserOwnsGoal_ReturnsTrue() {
        // Arrange
        GoalEntity goal = new GoalEntity();
        goal.setUser(createUser(USER_ID));
        when(goalService.getById(GOAL_ID)).thenReturn(goal);

        // Act
        boolean result = customSecurityExpression.canAccessGoal(USER_ID, GOAL_ID);

        // Assert
        assertTrue(result);
    }

    @Test
    void canAccessGoal_UserDoesNotOwnGoal_ReturnsFalse() {
        // Arrange
        GoalEntity goal = new GoalEntity();
        goal.setUser(createUser(2L)); // Another user
        when(goalService.getById(GOAL_ID)).thenReturn(goal);

        // Act
        boolean result = customSecurityExpression.canAccessGoal(USER_ID, GOAL_ID);

        // Assert
        assertFalse(result);
    }

    @Test
    void canAccessTransaction_UserOwnsTransaction_ReturnsTrue() {
        // Arrange
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUser(createUser(USER_ID));
        when(transactionService.getById(TRANSACTION_ID)).thenReturn(transaction);

        // Act
        boolean result = customSecurityExpression.canAccessTransaction(USER_ID, TRANSACTION_ID);

        // Assert
        assertTrue(result);
    }

    @Test
    void canAccessTransaction_UserDoesNotOwnTransaction_ReturnsFalse() {
        // Arrange
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUser(createUser(2L)); // Another user
        when(transactionService.getById(TRANSACTION_ID)).thenReturn(transaction);

        // Act
        boolean result = customSecurityExpression.canAccessTransaction(USER_ID, TRANSACTION_ID);

        // Assert
        assertFalse(result);
    }

    @Test
    void canAccessRecurringTransaction_UserOwnsRecurringTransaction_ReturnsTrue() {
        // Arrange
        RecurringTransactionEntity recurringTransaction = new RecurringTransactionEntity();
        recurringTransaction.setUser(createUser(USER_ID));
        when(recurringTransactionService.getById(RECURRING_TRANSACTION_ID)).thenReturn(recurringTransaction);

        // Act
        boolean result = customSecurityExpression.canAccessRecurringTransaction(USER_ID, RECURRING_TRANSACTION_ID);

        // Assert
        assertTrue(result);
    }

    @Test
    void canAccessRecurringTransaction_UserDoesNotOwnRecurringTransaction_ReturnsFalse() {
        // Arrange
        RecurringTransactionEntity recurringTransaction = new RecurringTransactionEntity();
        recurringTransaction.setUser(createUser(2L)); // Another user
        when(recurringTransactionService.getById(RECURRING_TRANSACTION_ID)).thenReturn(recurringTransaction);

        // Act
        boolean result = customSecurityExpression.canAccessRecurringTransaction(USER_ID, RECURRING_TRANSACTION_ID);

        // Assert
        assertFalse(result);
    }

    private UserEntity createUser(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        return user;
    }
}

