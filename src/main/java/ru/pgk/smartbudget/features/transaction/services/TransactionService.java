package ru.pgk.smartbudget.features.transaction.services;

import org.springframework.data.domain.Page;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

public interface TransactionService {
    Page<TransactionEntity> getAllByUserId(Long userId, Integer offset, Integer limit);

    TransactionEntity create(Long userId, CreateTransactionParams params);
}
