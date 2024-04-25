package ru.pgk.smartbudget.features.goal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

    Page<GoalEntity> findAllByUserId(Long userId, Pageable pageable);
}
