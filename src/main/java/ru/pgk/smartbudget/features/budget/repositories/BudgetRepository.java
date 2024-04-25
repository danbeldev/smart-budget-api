package ru.pgk.smartbudget.features.budget.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {

    Page<BudgetEntity> findAllByUserId(Long userId, Pageable pageable);
}
