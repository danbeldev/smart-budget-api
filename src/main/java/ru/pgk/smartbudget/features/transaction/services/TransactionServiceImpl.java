package ru.pgk.smartbudget.features.transaction.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;
import ru.pgk.smartbudget.features.currency.services.CurrencyService;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.features.transaction.repositories.TransactionRepository;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.dto.params.GetTransactionsParams;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.specifications.TransactionSpecifications;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ExpenseCategoryService expenseCategoryService;
    private final CurrencyService currencyService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionEntity> getAll(GetTransactionsParams params) {
        Specification<TransactionEntity> spec = Specification.where(null);
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());

        if(params.getUserId() != null) {
            spec = spec.and(TransactionSpecifications.byUserId(params.getUserId()));
        }

        if(params.getSearch() != null && !params.getSearch().isEmpty()) {
            spec = spec.and(TransactionSpecifications.likeByDescription(params.getSearch()));
        }

        if(params.getCategoryId() != null) {
            spec = spec.and(TransactionSpecifications.byCategoryId(params.getCategoryId()));
        }

        if(params.getCurrencyCodeId() != null) {
            spec = spec.and(TransactionSpecifications.byCurrencyCodeId(params.getCurrencyCodeId()));
        }

        if(params.getStartDate() != null) {
            spec = spec.and(TransactionSpecifications.dateGreaterThanOrEqual(params.getStartDate()));
        }

        if(params.getEndDate() != null) {
            spec = spec.and(TransactionSpecifications.dateLessThanOrEqual(params.getEndDate()));
        }

        if(params.getOrderByType() != null) {
            Sort sort = Sort.by(params.getOrderByType().getFieldName());
            if (params.getOrderByType().isDescending()) {
                sort = sort.descending();
            }
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        return transactionRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionEntity> getAllByDate(Long userId, LocalDate startDate, LocalDate endDate) {
        Specification<TransactionEntity> spec = Specification.where(null);

        if(userId != null) {
            spec = spec.and(TransactionSpecifications.byUserId(userId));
        }

        if(startDate != null) {
            spec = spec.and(TransactionSpecifications.dateGreaterThanOrEqual(startDate));
        }

        if(endDate != null) {
            spec = spec.and(TransactionSpecifications.dateLessThanOrEqual(endDate));
        }

        return transactionRepository.findAll(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionEntity> getAll(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAll().stream()
                .filter(transaction ->
                        (userId == null || transaction.getUser().getId().equals(userId)) &&
                                (startDate == null || !transaction.getDate().isBefore(startDate)) &&
                                (endDate == null || !transaction.getDate().isAfter(endDate)))
                .collect(Collectors.toList());
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

    @Override
    @Transactional
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}
