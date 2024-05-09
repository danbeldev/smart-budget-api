package ru.pgk.smartbudget.features.expenseCategory;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

import java.util.List;

@RestController
@RequestMapping("expense-categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    private final ExpenseCategoryMapper expenseCategoryMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private List<ExpenseCategoryDto> getAll(
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return expenseCategoryMapper.toDto(expenseCategoryService.getAll(jwtEntity.getUserId()));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    private ExpenseCategoryDto create(
            @RequestParam String name,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return expenseCategoryMapper.toDto(expenseCategoryService.create(name, jwtEntity.getUserId()));
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(
            @PathVariable Short id
    ) {
        expenseCategoryService.deleteById(id);
    }
}
