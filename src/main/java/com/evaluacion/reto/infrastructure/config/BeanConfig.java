package com.evaluacion.reto.infrastructure.config;

import com.evaluacion.reto.application.UserServices;
import com.evaluacion.reto.application.UserUseCase;
import com.evaluacion.reto.application.repository.UserPersistencePort;
import com.evaluacion.reto.infrastructure.database.mapper.UserEntityMapper;
import com.evaluacion.reto.infrastructure.rest.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserUseCase userUseCase(UserPersistencePort userPersistencePort) {
        return new UserServices(userPersistencePort);
    }
    @Bean
    public UserEntityMapper userEntityMapper() {
        return UserEntityMapper.INSTANCE;
    }
    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }
}
