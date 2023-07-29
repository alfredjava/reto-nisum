package com.evaluacion.reto.infrastructure.rest.dto;

import java.util.List;

@lombok.Data
public class UserRequest {
    /**
     * {
     * "name": "Juan Rodriguez",
     * "email": "juan@rodriguez.org",
     * "password": "hunter2",
     * "phones": [
     * {
     * "number": "1234567",
     * "citycode": "1",
     * "contrycode": "57"
     * }
     * ]
     * }
     */
    // generate attributes here
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

}
