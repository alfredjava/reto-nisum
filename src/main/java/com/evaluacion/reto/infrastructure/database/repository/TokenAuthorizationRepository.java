package com.evaluacion.reto.infrastructure.database.repository;


import com.evaluacion.reto.infrastructure.database.entity.TokenAuthorization;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TokenAuthorizationRepository extends ReactiveCrudRepository<TokenAuthorization, UUID> {

    Mono<TokenAuthorization> findByToken(String token);
}
