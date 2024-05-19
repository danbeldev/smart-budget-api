package ru.pgk.smartbudget.features.expenseCategory.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.expenseCategory.ExpenseCategoryRepository;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseCategoryServiceImplTest {

    @Mock
    private ExpenseCategoryRepository categoryRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ExpenseCategoryServiceImpl expenseCategoryService;

    @Test
    void getById_ExistingCategoryId_ReturnsExpenseCategoryEntity() {
        // Arrange
        Integer categoryId = 1;
        ExpenseCategoryEntity category = new ExpenseCategoryEntity();
        category.setName("Food");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        ExpenseCategoryEntity result = expenseCategoryService.getById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals("Food", result.getName());
    }

    @Test
    void getById_NonExistingCategoryId_ThrowsResourceNotFoundException() {
        // Arrange
        Integer categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> expenseCategoryService.getById(categoryId));
    }

    @Test
    void create_ValidCategoryNameAndUserId_ReturnsExpenseCategoryEntity() {
        // Arrange
        Long userId = 1L;
        String categoryName = "Food";
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userService.getById(userId)).thenReturn(user);

        ExpenseCategoryEntity category = new ExpenseCategoryEntity();
        category.setName(categoryName);
        category.setUser(user);
        when(categoryRepository.save(any())).thenReturn(category);

        // Act
        ExpenseCategoryEntity result = expenseCategoryService.create(categoryName, userId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryName, result.getName());
        assertEquals(userId, result.getUser().getId());
    }

    @Test
    void deleteById_ExistingCategoryId_DeletesCategory() {
        // Arrange
        Integer categoryId = 1;

        // Act
        expenseCategoryService.deleteById(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
