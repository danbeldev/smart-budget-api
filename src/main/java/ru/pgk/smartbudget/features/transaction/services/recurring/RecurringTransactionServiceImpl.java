package ru.pgk.smartbudget.features.transaction.services.recurring;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.transaction.dto.recurring.params.CreateRecurringTransactionParams;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;
import ru.pgk.smartbudget.features.transaction.repositories.recurring.RecurringTransactionRepository;
import ru.pgk.smartbudget.features.transaction.services.recurring.frequency.RecurringTransactionFrequencyService;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecurringTransactionServiceImpl implements RecurringTransactionService {

    private final RecurringTransactionRepository recurringTransactionRepository;

    private final UserService userService;
    private final ExpenseCategoryService expenseCategoryService;
    private final RecurringTransactionFrequencyService recurringTransactionFrequencyService;

    @Override
    @Transactional(readOnly = true)
    public Page<RecurringTransactionEntity> getAll(Long userId, Integer offset, Integer limit) {
        return recurringTransactionRepository.findAllByUserId(userId, PageRequest.of(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public RecurringTransactionEntity getById(Long id) {
        return recurringTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring transaction not found"));
    }

    @Override
    @Transactional
    public RecurringTransactionEntity add(Long userId, CreateRecurringTransactionParams params) {
        RecurringTransactionEntity recurringTransaction = new RecurringTransactionEntity();

        UserEntity user = userService.getById(userId);
        ExpenseCategoryEntity category = expenseCategoryService.getById(params.categoryId());
        RecurringTransactionFrequencyEntity recurringTransactionFrequency =
                recurringTransactionFrequencyService.getByName(params.frequency());

        recurringTransaction.setFrequency(recurringTransactionFrequency);
        recurringTransaction.setCategory(category);
        recurringTransaction.setUser(user);

        recurringTransaction.setName(recurringTransaction.getName());
        recurringTransaction.setAmount(recurringTransaction.getAmount());
        recurringTransaction.setStartDate(recurringTransaction.getStartDate());
        recurringTransaction.setEndDate(params.endDate());

        return recurringTransactionRepository.save(recurringTransaction);
    }

    @Override
    @Transactional
    public void makeInactive(Long id) {
        RecurringTransactionEntity recurringTransaction = getById(id);
        recurringTransaction.setEndDate(LocalDate.now());
        recurringTransactionRepository.save(recurringTransaction);
    }
}
