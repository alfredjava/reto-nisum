package com.evaluacion.reto.infrastructure.rest;

import com.evaluacion.reto.application.UserUseCase;
import com.evaluacion.reto.infrastructure.rest.dto.UserDto;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import com.evaluacion.reto.infrastructure.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public Mono<ResponseEntity<UserDto>> saveUser(@RequestBody UserRequest userRequest) {

        return userUseCase.saveUser(userRequest).map(user -> new ResponseEntity<>(userMapper.toDto(user),

                HttpStatus.CREATED));

    }
}
