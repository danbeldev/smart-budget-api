package ru.pgk.smartbudget.features.budget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>, JpaSpecificationExecutor<BudgetEntity> {}
