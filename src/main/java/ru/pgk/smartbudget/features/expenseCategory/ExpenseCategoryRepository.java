package ru.pgk.smartbudget.features.expenseCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

public interface ExpenseCategoryRepository
        extends JpaRepository<ExpenseCategoryEntity, Integer>, JpaSpecificationExecutor<ExpenseCategoryEntity> { }
