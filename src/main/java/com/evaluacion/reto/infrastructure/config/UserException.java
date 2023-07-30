package com.evaluacion.reto.infrastructure.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class UserException extends RuntimeException{

    private final String description;
    private final int statusCode;
    private final String systemCode;
}
