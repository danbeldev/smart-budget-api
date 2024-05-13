package ru.pgk.smartbudget.features.goal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;

public interface GoalRepository extends JpaRepository<GoalEntity, Long>, JpaSpecificationExecutor<GoalEntity> {

    Page<GoalEntity> findAllByUserId(Long userId, Pageable pageable);
}
