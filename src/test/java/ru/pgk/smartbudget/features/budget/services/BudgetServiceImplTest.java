package ru.pgk.smartbudget.features.budget.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.budget.dto.params.CreateOrUpdateBudgetParams;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;
import ru.pgk.smartbudget.features.budget.repositories.BudgetRepository;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserService userService;

    @Mock
    private ExpenseCategoryService expenseCategoryService;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private static final Long BUDGET_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Integer CATEGORY_ID = 1;

    @Test
    void getById_BudgetExists_ReturnsBudgetEntity() {
        // Arrange
        BudgetEntity budget = new BudgetEntity();
        budget.setId(BUDGET_ID);
        when(budgetRepository.findById(BUDGET_ID)).thenReturn(Optional.of(budget));

        // Act
        BudgetEntity result = budgetService.getById(BUDGET_ID);

        // Assert
        assertNotNull(result);
        assertEquals(BUDGET_ID, result.getId());
    }

    @Test
    void getById_BudgetDoesNotExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(budgetRepository.findById(BUDGET_ID)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> budgetService.getById(BUDGET_ID));
    }

    @Test
    void create_ValidParameters_ReturnsBudgetEntity() {
        // Arrange
        CreateOrUpdateBudgetParams params = new CreateOrUpdateBudgetParams();
        params.setCategoryId(CATEGORY_ID);
        params.setAmountLimit(100.0);
        BudgetEntity budget = new BudgetEntity();
        when(userService.getById(USER_ID)).thenReturn(new UserEntity());
        when(expenseCategoryService.getById(CATEGORY_ID)).thenReturn(new ExpenseCategoryEntity());
        when(budgetRepository.save(any(BudgetEntity.class))).thenReturn(budget);

        // Act
        BudgetEntity result = budgetService.create(USER_ID, params);

        // Assert
        assertNotNull(result);
    }

    @Test
    void update_ValidParameters_CallsSave() {
        // Arrange
        CreateOrUpdateBudgetParams params = new CreateOrUpdateBudgetParams();
        params.setCategoryId(CATEGORY_ID);
        BudgetEntity budget = new BudgetEntity();
        when(budgetRepository.findById(BUDGET_ID)).thenReturn(Optional.of(budget));
        when(expenseCategoryService.getById(CATEGORY_ID)).thenReturn(new ExpenseCategoryEntity());

        // Act
        budgetService.update(BUDGET_ID, params);

        // Assert
        verify(budgetRepository, times(1)).save(budget);
    }

    @Test
    void deleteById_ValidBudgetId_CallsDeleteById() {
        // Act
        budgetService.deleteById(BUDGET_ID);

        // Assert
        verify(budgetRepository, times(1)).deleteById(BUDGET_ID);
    }
}
