package ru.pgk.smartbudget.features.user.mappers;

import org.mapstruct.Mapper;
import ru.pgk.smartbudget.common.mapper.Mappable;
import ru.pgk.smartbudget.features.user.dto.UserDto;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<UserEntity, UserDto> {}
