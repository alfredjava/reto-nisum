package com.evaluacion.reto.infrastructure.database.repository;


import com.evaluacion.reto.infrastructure.database.entity.TokenAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AuthorizathionService implements AuthorizationService {

    @Autowired
    private TokenAuthorizationRepository authorizationRepository;



    public Mono<TokenAuthorization> generateToken() {

        return authorizationRepository.save(TokenAuthorization.builder()
                .token(UUID.randomUUID().toString()).build());

    }
}
