package ru.pgk.smartbudget.features.transaction.services.recurring.frequency;

import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;

public interface RecurringTransactionFrequencyService {

    RecurringTransactionFrequencyEntity getByName(RecurringTransactionFrequencyEntity.Name name);
}
