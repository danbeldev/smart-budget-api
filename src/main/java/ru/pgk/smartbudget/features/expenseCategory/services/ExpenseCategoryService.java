package ru.pgk.smartbudget.features.expenseCategory.services;

import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

import java.util.List;

public interface ExpenseCategoryService {

    List<ExpenseCategoryEntity> getAll(Long userId);

    ExpenseCategoryEntity getById(Short id);

    ExpenseCategoryEntity create(String name, Long userId);

    void deleteById(Short id);
}
