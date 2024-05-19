package ru.pgk.smartbudget.features.transaction.services.recurring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;
import ru.pgk.smartbudget.features.transaction.repositories.recurring.RecurringTransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecurringTransactionServiceImplTest {

    @Mock
    private RecurringTransactionRepository recurringTransactionRepository;


    @InjectMocks
    private RecurringTransactionServiceImpl recurringTransactionService;

    @Test
    void getAll_ValidUserIdAndParams_ReturnsPageOfRecurringTransactions() {
        // Arrange
        Long userId = 1L;
        Integer offset = 0;
        Integer limit = 10;

        List<RecurringTransactionEntity> transactions = new ArrayList<>();
        transactions.add(new RecurringTransactionEntity());
        transactions.add(new RecurringTransactionEntity());
        Page<RecurringTransactionEntity> page = new PageImpl<>(transactions);
        when(recurringTransactionRepository.findAllByUserId(userId, PageRequest.of(offset, limit))).thenReturn(page);

        // Act
        Page<RecurringTransactionEntity> result = recurringTransactionService.getAll(userId, offset, limit);

        // Assert
        assertNotNull(result);
        assertEquals(transactions.size(), result.getContent().size());
    }

    @Test
    void getById_ExistingRecurringTransactionId_ReturnsRecurringTransactionEntity() {
        // Arrange
        Long transactionId = 1L;
        RecurringTransactionEntity transaction = new RecurringTransactionEntity();
        when(recurringTransactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // Act
        RecurringTransactionEntity result = recurringTransactionService.getById(transactionId);

        // Assert
        assertNotNull(result);
        assertSame(transaction, result);
    }

    @Test
    void getById_NonExistingRecurringTransactionId_ThrowsResourceNotFoundException() {
        // Arrange
        Long transactionId = 1L;
        when(recurringTransactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> recurringTransactionService.getById(transactionId));
    }

    @Test
    void makeInactive_ExistingRecurringTransactionId_SetsEndDateToToday() {
        // Arrange
        Long transactionId = 1L;
        RecurringTransactionEntity transaction = new RecurringTransactionEntity();
        when(recurringTransactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        LocalDate today = LocalDate.now();

        // Act
        recurringTransactionService.makeInactive(transactionId);

        // Assert
        assertEquals(today, transaction.getEndDate());
    }
}
