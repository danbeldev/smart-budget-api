package ru.pgk.smartbudget.features.expenseCategory.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

public class ExpenseCategorySpecifications {

    public static Specification<ExpenseCategoryEntity> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<ExpenseCategoryEntity> userIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("user"));
    }

    public static Specification<ExpenseCategoryEntity> likeByName(String s) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + s.toLowerCase() + "%");
    }
}
