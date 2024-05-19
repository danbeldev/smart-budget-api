package ru.pgk.smartbudget.features.transaction.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.repositories.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void getById_WhenTransactionExists_ReturnTransaction() {
        // Arrange
        long transactionId = 1L;
        TransactionEntity mockTransaction = new TransactionEntity();
        mockTransaction.setId(transactionId);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(mockTransaction));

        // Act
        TransactionEntity result = transactionService.getById(transactionId);

        // Assert
        assertNotNull(result);
        assertEquals(transactionId, result.getId());
    }

    @Test
    void getById_WhenTransactionNotExists_ThrowResourceNotFoundException() {
        // Arrange
        long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.getById(transactionId);
        });
    }

    @Test
    void getAll_WithoutParams_ReturnListOfTransactions() {
        // Arrange
        List<TransactionEntity> mockList = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(mockList);

        // Act
        List<TransactionEntity> result = transactionService.getAll(null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(mockList, result);
    }

    @Test
    void deleteById_ExistingTransaction_DeleteTransaction() {
        // Arrange
        Long transactionId = 1L;

        // Act
        transactionService.deleteById(transactionId);

        // Assert
        verify(transactionRepository, times(1)).deleteById(transactionId);
    }
}

