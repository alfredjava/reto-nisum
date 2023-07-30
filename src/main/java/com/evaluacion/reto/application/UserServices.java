package com.evaluacion.reto.application;


import com.evaluacion.reto.application.repository.UserPersistencePort;
import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class UserServices implements UserUseCase{

    private final UserPersistencePort userPersistencePort;

    @Override
    public Mono<User> saveUser(UserRequest userRequest) {
        return userPersistencePort.saveUser(userRequest);
    }

    @Override
    public Mono<User> login(String email, String password) {
        return userPersistencePort.login(email,password);
    }
}
