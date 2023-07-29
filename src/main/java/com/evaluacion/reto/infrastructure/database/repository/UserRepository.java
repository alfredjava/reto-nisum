package com.evaluacion.reto.infrastructure.database.repository;

import com.evaluacion.reto.application.repository.UserPersistencePort;
import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.database.mapper.UserEntityMapper;
import com.evaluacion.reto.infrastructure.rest.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class UserRepository implements UserPersistencePort {
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    @Override
    public Mono<User> saveUser(UserRequest userRequest) {


        return Mono.fromCallable(() -> userJpaRepository.save(userEntityMapper.toEntity(userRequest)))
                .map(userEntity -> userEntityMapper.toDomain(userEntity.block())).subscribeOn(Schedulers.boundedElastic());



    }
}
