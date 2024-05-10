package ru.pgk.smartbudget.features.budget.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;

import java.time.LocalDate;

public class BudgetSpecifications {

    public static Specification<BudgetEntity> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<BudgetEntity> byCategoryId(Integer categoryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<BudgetEntity> startDateGreaterThanOrEqual(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<BudgetEntity> endDateLessThanOrEqual(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }
}
