package ru.pgk.smartbudget.features.transaction.services.recurring.frequency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;
import ru.pgk.smartbudget.features.transaction.repositories.recurring.frequency.RecurringTransactionFrequencyRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecurringTransactionFrequencyServiceImplTest {

    @Mock
    private RecurringTransactionFrequencyRepository recurringTransactionFrequencyRepository;

    @InjectMocks
    private RecurringTransactionFrequencyServiceImpl recurringTransactionFrequencyService;

    @Test
    void getByName_ExistingFrequencyName_ReturnsFrequencyEntity() {
        // Arrange
        RecurringTransactionFrequencyEntity.Name frequencyName = RecurringTransactionFrequencyEntity.Name.MONTHLY;
        RecurringTransactionFrequencyEntity frequencyEntity = new RecurringTransactionFrequencyEntity();
        frequencyEntity.setName(frequencyName);

        when(recurringTransactionFrequencyRepository.findByName(frequencyName))
                .thenReturn(Optional.of(frequencyEntity));

        // Act
        RecurringTransactionFrequencyEntity result = recurringTransactionFrequencyService.getByName(frequencyName);

        // Assert
        assertNotNull(result);
        assertEquals(frequencyEntity, result);
    }

    @Test
    void getByName_NonExistingFrequencyName_ThrowsResourceNotFoundException() {
        // Arrange
        RecurringTransactionFrequencyEntity.Name frequencyName = RecurringTransactionFrequencyEntity.Name.MONTHLY;
        when(recurringTransactionFrequencyRepository.findByName(frequencyName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> recurringTransactionFrequencyService.getByName(frequencyName));
    }
}