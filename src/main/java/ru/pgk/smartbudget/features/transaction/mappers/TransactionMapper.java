package ru.pgk.smartbudget.features.transaction.mappers;

import org.mapstruct.Mapper;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.currency.mappers.CurrencyMapper;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.transaction.dto.TransactionDto;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

@Mapper(componentModel = "spring", uses = {ExpenseCategoryMapper.class, CurrencyMapper.class})
public interface TransactionMapper extends Mappable<TransactionEntity, TransactionDto> {}
