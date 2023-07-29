package com.evaluacion.reto.infrastructure.database.repository;

import com.evaluacion.reto.infrastructure.database.entity.TokenAuthorization;
import reactor.core.publisher.Mono;

public interface AuthorizationService {
    Mono<TokenAuthorization> generateToken();
}
