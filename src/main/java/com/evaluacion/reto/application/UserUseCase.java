package com.evaluacion.reto.application;

import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.domain.UserRequest;
import reactor.core.publisher.Mono;


public interface UserUseCase {

    Mono<User> saveUser(UserRequest userRequest) ;
    Mono<User> login(String email, String password);

}
