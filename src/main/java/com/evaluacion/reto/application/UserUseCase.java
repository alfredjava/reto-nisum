package com.evaluacion.reto.application;

import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import reactor.core.publisher.Mono;


public interface UserUseCase {

    Mono<User> saveUser(UserRequest userRequest) ;
    Mono<User> login(String email, String password);

}
