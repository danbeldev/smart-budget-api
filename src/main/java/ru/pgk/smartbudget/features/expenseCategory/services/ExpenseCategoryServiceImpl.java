package ru.pgk.smartbudget.features.expenseCategory.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.expenseCategory.ExpenseCategoryRepository;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository categoryRepository;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseCategoryEntity> getAll(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseCategoryEntity getById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    @Transactional
    public ExpenseCategoryEntity create(String name, Long userId) {
        UserEntity user = userService.getById(userId);

        ExpenseCategoryEntity expenseCategoryEntity = new ExpenseCategoryEntity();
        expenseCategoryEntity.setName(name);
        expenseCategoryEntity.setUser(user);

        return categoryRepository.save(expenseCategoryEntity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
