package ru.pgk.smartbudget.features.transaction.mappers.recurring;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.transaction.dto.recurring.RecurringTransactionDto;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;

@Mapper(componentModel = "spring", uses = ExpenseCategoryMapper.class)
public interface RecurringTransactionMapper extends Mappable<RecurringTransactionEntity, RecurringTransactionDto> {

    @Override
    @Mapping(source = "entity.frequency.name", target = "frequency")
    RecurringTransactionDto toDto(RecurringTransactionEntity entity);

    @Override
    @Mapping(source = "dto.frequency", target = "frequency.name")
    RecurringTransactionEntity toEntity(RecurringTransactionDto dto);
}
