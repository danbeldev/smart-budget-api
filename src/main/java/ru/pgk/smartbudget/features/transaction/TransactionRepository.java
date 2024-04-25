package ru.pgk.smartbudget.features.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Page<TransactionEntity> getAllByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    List<TransactionEntity> getAllByUserId(Long userId);
}
