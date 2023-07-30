package com.evaluacion.reto.infrastructure.rest;

import com.evaluacion.reto.application.UserUseCase;
import com.evaluacion.reto.infrastructure.rest.dto.UserDto;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import com.evaluacion.reto.infrastructure.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public Mono<ResponseEntity<UserDto>> login(@RequestBody @Valid UserRequest userRequest) {

        return userUseCase.login(userRequest.getEmail(), userRequest.getPassword())
                .flatMap(user -> Mono.just(new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK)))
                .switchIfEmpty(userUseCase.saveUser(userRequest)
                        .doOnSuccess(user -> log.info("User created: {}", user))
                        .map(user -> new ResponseEntity<>(userMapper.toDto(user),
                        HttpStatus.CREATED)).doOnError(error -> Mono.error(new Exception("Error creating user"))));



    }
}
