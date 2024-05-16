package ru.pgk.smartbudget.features.transaction.services.recurring;

import org.springframework.data.domain.Page;
import ru.pgk.smartbudget.features.transaction.dto.recurring.params.CreateRecurringTransactionParams;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;

public interface RecurringTransactionService {

    Page<RecurringTransactionEntity> getAll(Long userId, Integer offset, Integer limit);

    RecurringTransactionEntity getById(Long id);

    RecurringTransactionEntity add(Long userId, CreateRecurringTransactionParams params);

    void updateIsAchieved(Long id, Boolean isAchieved);
}
