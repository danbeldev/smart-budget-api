package ru.pgk.smartbudget.features.goal.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;

import java.time.LocalDate;

public class GoalSpecifications {

    public static Specification<GoalEntity> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<GoalEntity> byIsAchieved(Boolean isAchieved) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isAchieved"), isAchieved);
    }

    public static Specification<GoalEntity> deadlineGreaterThanOrEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("deadline"), date);
    }

    public static Specification<GoalEntity> deadlineLessThanOrEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("deadline"), date);
    }


    public static Specification<GoalEntity> likeByName(String s) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + s.toLowerCase() + "%");
    }
}