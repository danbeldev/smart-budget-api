package ru.pgk.smartbudget.features.transaction.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.time.LocalDate;

public class TransactionSpecifications {


    public static Specification<TransactionEntity> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<TransactionEntity> byCurrencyCodeId(Short currencyCodeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("currencyCode").get("id"), currencyCodeId);
    }

    public static Specification<TransactionEntity> byCategoryId(Integer categoryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<TransactionEntity> dateGreaterThanOrEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<TransactionEntity> dateLessThanOrEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<TransactionEntity> likeByDescription(String s) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + s.toLowerCase() + "%");
    }
}
