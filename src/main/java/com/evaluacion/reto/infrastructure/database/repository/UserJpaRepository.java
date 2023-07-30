package com.evaluacion.reto.infrastructure.database.repository;




import com.evaluacion.reto.infrastructure.database.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserJpaRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserEntity> findByEmail(String email);
    Mono<UserEntity> findByEmailAndPassword(String email, String password);
}
