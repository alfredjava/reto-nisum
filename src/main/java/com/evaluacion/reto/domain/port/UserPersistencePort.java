package com.evaluacion.reto.domain.port;


import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.domain.UserRequest;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<User> saveUser(UserRequest userRequest);
    Mono<User> findByEmail(String email);

    Mono<User> login(String email, String password);

}
