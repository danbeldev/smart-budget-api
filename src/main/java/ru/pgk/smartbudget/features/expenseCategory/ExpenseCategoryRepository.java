package ru.pgk.smartbudget.features.expenseCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Short> {

    @Query("SELECT c FROM expense_categories c WHERE c.user == NULL OR c.user.id = :userId")
    List<ExpenseCategoryEntity> findAllByUserId(Long userId);
}
