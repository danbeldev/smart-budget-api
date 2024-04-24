package ru.pgk.smartbudget.features.expenseCategory.services;

import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

import java.util.List;

public interface ExpenseCategoryService {

    List<ExpenseCategoryEntity> getAll();
    ExpenseCategoryEntity getById(Short id);
}
