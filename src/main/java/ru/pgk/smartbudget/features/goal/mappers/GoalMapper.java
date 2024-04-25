package ru.pgk.smartbudget.features.goal.mappers;

import org.mapstruct.Mapper;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.goal.dto.GoalDto;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;

@Mapper(componentModel = "spring")
public interface GoalMapper extends Mappable<GoalEntity, GoalDto> {}
