package ru.pgk.smartbudget.features.transaction.services.recurring.frequency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;
import ru.pgk.smartbudget.features.transaction.repositories.recurring.frequency.RecurringTransactionFrequencyRepository;

@Service
@RequiredArgsConstructor
public class RecurringTransactionFrequencyServiceImpl implements RecurringTransactionFrequencyService {

    private final RecurringTransactionFrequencyRepository recurringTransactionFrequencyRepository;

    @Override
    @Transactional(readOnly = true)
    public RecurringTransactionFrequencyEntity getByName(RecurringTransactionFrequencyEntity.Name name) {
        return recurringTransactionFrequencyRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring transaction frequency not found"));
    }
}
