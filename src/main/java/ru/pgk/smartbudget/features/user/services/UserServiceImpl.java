package ru.pgk.smartbudget.features.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.user.UserRepository;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
