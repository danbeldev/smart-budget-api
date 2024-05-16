package ru.pgk.smartbudget.features.transaction.repositories.recurring;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;

public interface RecurringTransactionRepository extends JpaRepository<RecurringTransactionEntity, Long> {

    Page<RecurringTransactionEntity> findAllByUserId(Long userId, Pageable pageable);
}
