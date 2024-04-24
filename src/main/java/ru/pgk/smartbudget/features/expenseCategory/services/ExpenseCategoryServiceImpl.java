package ru.pgk.smartbudget.features.expenseCategory.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.expenseCategory.ExpenseCategoryRepository;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseCategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseCategoryEntity getById(Short id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
