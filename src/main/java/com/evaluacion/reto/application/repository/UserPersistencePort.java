package com.evaluacion.reto.application.repository;


import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<User> saveUser(UserRequest userRequest);
}
