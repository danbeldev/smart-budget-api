package ru.pgk.smartbudget.features.transaction.repositories.recurring.frequency;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;

import java.util.Optional;

public interface RecurringTransactionFrequencyRepository extends JpaRepository<RecurringTransactionFrequencyEntity, Short> {

    Optional<RecurringTransactionFrequencyEntity> findByName(RecurringTransactionFrequencyEntity.Name name);
}
