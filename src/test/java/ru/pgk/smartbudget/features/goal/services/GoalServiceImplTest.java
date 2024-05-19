package ru.pgk.smartbudget.features.goal.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.goal.GoalRepository;
import ru.pgk.smartbudget.features.goal.dto.params.CreateOrUpdateGoalParams;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private GoalServiceImpl goalService;

    @Test
    void getById_ExistingGoalId_ReturnsGoalEntity() {
        // Arrange
        Long goalId = 1L;
        GoalEntity goal = new GoalEntity();
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));

        // Act
        GoalEntity result = goalService.getById(goalId);

        // Assert
        assertNotNull(result);
        assertSame(goal, result);
    }

    @Test
    void getById_NonExistingGoalId_ThrowsResourceNotFoundException() {
        // Arrange
        Long goalId = 1L;
        when(goalRepository.findById(goalId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> goalService.getById(goalId));
    }

    @Test
    void create_ValidUserIdAndParams_ReturnsGoalEntity() {
        // Arrange
        Long userId = 1L;
        CreateOrUpdateGoalParams params = new CreateOrUpdateGoalParams();
        params.setName("New Goal");
        params.setDeadline(LocalDate.now().plusDays(30));
        params.setTargetAmount(2000.0);
        params.setCurrentAmount(500.0);

        UserEntity user = new UserEntity();
        user.setId(userId);
        when(userService.getById(userId)).thenReturn(user);

        GoalEntity goal = new GoalEntity();
        when(goalRepository.save(any())).thenReturn(goal);

        // Act
        GoalEntity result = goalService.create(userId, params);

        // Assert
        assertNotNull(result);
    }

    @Test
    void update_ExistingGoalIdAndParams_ReturnsUpdatedGoalEntity() {
        // Arrange
        Long goalId = 1L;
        CreateOrUpdateGoalParams params = new CreateOrUpdateGoalParams();
        params.setName("Updated Goal");
        params.setDeadline(LocalDate.now().plusDays(30));
        params.setTargetAmount(2000.0);
        params.setCurrentAmount(1500.0);

        GoalEntity goal = new GoalEntity();
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));
        when(goalRepository.save(any())).thenReturn(goal);

        // Act
        GoalEntity result = goalService.update(goalId, params);

        // Assert
        assertNotNull(result);
        assertEquals(params.getName(), result.getName());
        assertEquals(params.getDeadline(), result.getDeadline());
        assertEquals(params.getTargetAmount(), result.getTargetAmount());
        assertEquals(params.getCurrentAmount(), result.getCurrentAmount());
    }

    @Test
    void updateCurrentAmount_ExistingGoalIdAndAmount_UpdatesCurrentAmount() {
        // Arrange
        Long goalId = 1L;
        Double amount = 2000.0;
        GoalEntity goal = new GoalEntity();
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));
        when(goalRepository.save(any())).thenReturn(goal);

        // Act
        goalService.updateCurrentAmount(goalId, amount);

        // Assert
        assertEquals(amount, goal.getCurrentAmount());
    }

    @Test
    void deleteById_ExistingGoalId_DeletesGoal() {
        // Arrange
        Long goalId = 1L;

        // Act
        goalService.deleteById(goalId);

        // Assert
        verify(goalRepository, times(1)).deleteById(goalId);
    }
}
