package com.evaluacion.reto.infrastructure.rest;


import com.evaluacion.reto.infrastructure.database.entity.TokenAuthorization;
import com.evaluacion.reto.infrastructure.database.repository.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/tokens")
public class CsrfTokenController {

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TokenAuthorization> generateToken() {
        return authorizationService.generateToken();
    }
}
