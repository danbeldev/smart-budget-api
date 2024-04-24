package ru.pgk.smartbudget.features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
