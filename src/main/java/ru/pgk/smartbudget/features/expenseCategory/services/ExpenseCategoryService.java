package ru.pgk.smartbudget.features.expenseCategory.services;

import org.springframework.data.domain.Page;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

public interface ExpenseCategoryService {

    Page<ExpenseCategoryEntity> getAll(String search, Long userId, Integer offset, Integer limit);

    ExpenseCategoryEntity getById(Integer id);

    ExpenseCategoryEntity create(String name, Long userId);

    void deleteById(Integer id);
}
