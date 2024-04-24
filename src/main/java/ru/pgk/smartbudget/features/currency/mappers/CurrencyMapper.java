package ru.pgk.smartbudget.features.currency.mappers;

import org.mapstruct.Mapper;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.currency.dto.CurrencyDto;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;

@Mapper(componentModel = "spring")
public interface CurrencyMapper extends Mappable<CurrencyEntity, CurrencyDto> {}
