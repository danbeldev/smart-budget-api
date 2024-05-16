package ru.pgk.smartbudget.features.expenseCategory;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@RestController
@RequestMapping("expense-categories")
@RequiredArgsConstructor
@Tag(name = "Expense category")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    private final ExpenseCategoryMapper expenseCategoryMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<ExpenseCategoryDto> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return PageDto.fromPage(
                expenseCategoryService.getAll(search, jwtEntity.getUserId(), offset, limit)
                        .map(expenseCategoryMapper::toDto)
        );
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
            @PathVariable Integer id
    ) {
        expenseCategoryService.deleteById(id);
    }
}
