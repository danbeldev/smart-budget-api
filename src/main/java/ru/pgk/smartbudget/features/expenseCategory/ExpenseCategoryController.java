package ru.pgk.smartbudget.features.expenseCategory;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.expenseCategory.services.ExpenseCategoryService;
import ru.pgk.smartbudget.security.expressions.CustomSecurityExpression;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@Valid
@RestController
@RequestMapping("expense-categories")
@RequiredArgsConstructor
@Tag(name = "Expense category")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    private final ExpenseCategoryMapper expenseCategoryMapper;

    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<ExpenseCategoryDto> getAll(
            @RequestParam(required = false) String search,

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "offset must be greater than or equal to zero")
            Integer offset,

            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "limit must be greater than or equal to one")
            @Max(value = 100, message = "limit must be less than or equal to one hundred")
            Integer limit,

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
            @RequestParam
            @Length(max = 48, message = "name must be smaller than 48 symbols")
            String name,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return expenseCategoryMapper.toDto(expenseCategoryService.create(name, jwtEntity.getUserId()));
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(
            @PathVariable Integer id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(!customSecurityExpression.canAccessExpenseCategory(jwtEntity.getUserId(), id))
            throw new AccessDeniedException("Access is denied");

        expenseCategoryService.deleteById(id);
    }
}
