package com.evaluacion.reto.infrastructure.database.mapper;

import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.database.entity.UserEntity;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS,builder = @Builder(disableBuilder = true))
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserEntityMapper.class);
    @Mapping(target ="isActive",expression = "java(true)")
    @Mapping(target ="created",expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target ="modified",expression = "java(java.time.LocalDateTime.now())")
    UserEntity toEntity(UserRequest userRequest);


    User toDomain(UserEntity userEntity);

}
