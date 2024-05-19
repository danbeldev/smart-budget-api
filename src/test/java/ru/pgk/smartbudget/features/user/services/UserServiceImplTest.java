package ru.pgk.smartbudget.features.user.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.BadRequestException;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.user.UserRepository;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "test@example.com";

    @Test
    void getById_UserExists_ReturnsUserEntity() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(USER_ID);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.getById(USER_ID);

        // Assert
        assertNotNull(result);
        assertEquals(USER_ID, result.getId());
    }

    @Test
    void getById_UserDoesNotExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getById(USER_ID));
    }

    @Test
    void getByEmail_UserExists_ReturnsUserEntity() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setEmail(USER_EMAIL);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.getByEmail(USER_EMAIL);

        // Assert
        assertNotNull(result);
        assertEquals(USER_EMAIL, result.getEmail());
    }

    @Test
    void getByEmail_UserDoesNotExist_ThrowsResourceNotFoundException() {
        // Arrange
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail(USER_EMAIL));
    }

    @Test
    void add_UserWithEmailAlreadyExists_ThrowsBadRequestException() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setEmail(USER_EMAIL);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // Assert
        assertThrows(BadRequestException.class, () -> userService.add(user));
    }
}
