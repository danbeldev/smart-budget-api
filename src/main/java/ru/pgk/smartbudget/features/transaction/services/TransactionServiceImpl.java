package ru.pgk.smartbudget.features.transaction.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;
import ru.pgk.smartbudget.features.currency.services.CurrencyService;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.transaction.TransactionRepository;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ExpenseCategoryService expenseCategoryService;
    private final CurrencyService currencyService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionEntity> getAllByUserId(Long userId, Integer offset, Integer limit) {
        return transactionRepository.getAllByUserIdOrderByDateDesc(userId, PageRequest.of(offset, limit));
    }

    @Override
    @Transactional
    public TransactionEntity create(Long userId, CreateTransactionParams params) {
        UserEntity user = userService.getById(userId);
        ExpenseCategoryEntity category = expenseCategoryService.getById(params.getCategoryId());
        CurrencyEntity defaultCurrency = currencyService.getDefault();
        CurrencyEntity currencyCode = null;
        Double amountInCurrency = null;
        Double amountInBaseCurrency;
        if(params.getCurrencyCodeId() != null && !params.getCurrencyCodeId().equals(defaultCurrency.getId())) {
            currencyCode = currencyService.getById(params.getCurrencyCodeId());
            amountInCurrency = params.getAmount();
            amountInBaseCurrency = amountInCurrency * currencyService.getCourseByCode(currencyCode.getCode());
        }else {
            amountInBaseCurrency = params.getAmount();

        }
        TransactionEntity transaction = new TransactionEntity();
        transaction.setCategory(category);
        transaction.setUser(user);
        transaction.setCurrencyCode(currencyCode);
        transaction.setAmountInCurrency(amountInCurrency);
        transaction.setAmountInBaseCurrency(amountInBaseCurrency);
        transaction.setDate(params.getDate());
        transaction.setDescription(params.getDescription());
        transaction.setBaseCurrencyCode(defaultCurrency);
        return transactionRepository.save(transaction);
    }
}
