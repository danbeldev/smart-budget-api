package ru.pgk.smartbudget.features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}
