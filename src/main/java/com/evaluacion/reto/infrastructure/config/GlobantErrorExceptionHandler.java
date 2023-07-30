package com.evaluacion.reto.infrastructure.config;



import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Component
@Order(-9)
public class GlobantErrorExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobantErrorExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        var exception = this.getError(request);
        Map<String,Object> errorMap= new HashMap();
        HttpStatus httpStatus=null;

        if(exception instanceof UserException){

            UserException apiException=(UserException) exception;

            httpStatus=HttpStatus.valueOf(apiException.getStatusCode());
            errorMap.put("message",apiException.getDescription());

        }else{
            var exception1 = ((WebExchangeBindException) exception);
            httpStatus=exception1.getStatus();
            errorMap.put("message",exception1.getAllErrors().get(0).getDefaultMessage());
        }

        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(errorMap));
    }
}
