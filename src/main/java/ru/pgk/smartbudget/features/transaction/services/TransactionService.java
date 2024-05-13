package ru.pgk.smartbudget.features.transaction.services;

import org.springframework.data.domain.Page;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.dto.params.GetTransactionsParams;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    Page<TransactionEntity> getAll(GetTransactionsParams params);

    List<TransactionEntity> getAll(Long userId, LocalDate startDate, LocalDate endDate);

    TransactionEntity create(Long userId, CreateTransactionParams params);

    void deleteById(Long id);
}
