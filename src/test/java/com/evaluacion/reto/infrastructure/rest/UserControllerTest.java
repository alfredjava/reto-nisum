package com.evaluacion.reto.infrastructure.rest;

import com.evaluacion.reto.application.UserUseCase;
import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.config.UserException;
import com.evaluacion.reto.infrastructure.rest.dto.UserDto;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import com.evaluacion.reto.infrastructure.rest.mapper.UserMapper;
import org.junit.jupiter.api.Test;



import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    public void testLogin() {
        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        User user = new User();
        UUID id = UUID.randomUUID();

        user.setId(id);

        UserDto userDto = new UserDto();
        userDto.setId(id);

        when(userUseCase.login(anyString(), anyString())).thenReturn(Mono.just(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        Mono<ResponseEntity<UserDto>> response = userController.login(userRequest);

        // Then
        StepVerifier.create(response)
                .expectNextMatches(entity -> {
                    HttpStatus status = entity.getStatusCode();
                    UserDto body = entity.getBody();
                    return status == HttpStatus.OK && body.getId().equals(id) ;
                })
                .verifyComplete();
    }


    @Test
    void testUserSave() {
        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);

        UserDto userDto = new UserDto();
        userDto.setId(id);

        when(userUseCase.saveUser(userRequest)).thenReturn(Mono.just(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        Mono<ResponseEntity<UserDto>> response = userController.userSave(userRequest);

        // Then
        StepVerifier.create(response)
                .expectNextMatches(entity -> {
                    HttpStatus status = entity.getStatusCode();
                    UserDto body = entity.getBody();
                    return status == HttpStatus.CREATED && body.getId().equals(id);
                })
                .verifyComplete();
    }

    @Test
     void testUserSaveWithError() {
        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        User user = new User();
        user.setId(UUID.randomUUID());

        //when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userUseCase.saveUser(userRequest)).thenReturn(Mono.error(new RuntimeException("Error saving user")));

        // When
        Mono<ResponseEntity<UserDto>> response = userController.userSave(userRequest);

        // Then
        StepVerifier.create(response)
                .verifyErrorMessage("Error saving user");
    }

    @Test
    void testUserSaveWithErrorBadPaswordRequest() {
        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("passwordrerere$$$$");

        User user = new User();
        user.setId(UUID.randomUUID());

        //when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userUseCase.saveUser(userRequest)).thenReturn(Mono.error(
                UserException.builder().statusCode(400).description("mesagePassword").build()));

        // When
        Mono<ResponseEntity<UserDto>> response = userController.userSave(userRequest);

        // Then
        StepVerifier.create(response)
                .expectErrorMessage("mesagePassword");
    }


}
