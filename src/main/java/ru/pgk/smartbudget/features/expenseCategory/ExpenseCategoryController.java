package ru.pgk.smartbudget.features.expenseCategory;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;

import java.util.List;

@RestController
@RequestMapping("expense-categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    private final ExpenseCategoryMapper expenseCategoryMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private List<ExpenseCategoryDto> getAll() {
        return expenseCategoryMapper.toDto(expenseCategoryService.getAll());
    }
}
