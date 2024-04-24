package ru.pgk.smartbudget.features.expenseCategory.mappers;

import org.mapstruct.Mapper;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;

@Mapper(componentModel = "spring")
public interface ExpenseCategoryMapper extends Mappable<ExpenseCategoryEntity, ExpenseCategoryDto> {}
