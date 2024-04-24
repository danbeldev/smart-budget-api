package ru.pgk.smartbudget.features.user.services;

import ru.pgk.smartbudget.features.user.entities.UserEntity;

public interface UserService {

    UserEntity getById(Long id);

    UserEntity getByEmail(String email);

    UserEntity add(UserEntity user);
}
