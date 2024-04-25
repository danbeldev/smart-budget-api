package ru.pgk.smartbudget.features.budget.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.budget.dto.BudgetDto;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;

@Mapper(componentModel = "spring")
public interface BudgetMapper extends Mappable<BudgetEntity, BudgetDto> {

    @Override
    @Mapping(expression = "java(entity.getCurrentAmount())", target = "currentAmount")
    BudgetDto toDto(BudgetEntity entity);
}
