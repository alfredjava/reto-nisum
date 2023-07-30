package com.evaluacion.reto.infrastructure.rest.mapper;

import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.rest.dto.UserDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS,builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    @Mapping(target ="token",source = "token")
    UserDto toDto(User user);
}
