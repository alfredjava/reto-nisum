package com.evaluacion.reto.domain;

import javax.validation.constraints.*;
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
    @NotBlank(message = "El campo 'name' no puede estar en blanco")
    @Size(max = 100, message = "El campo 'name' debe tener como máximo {max} caracteres")
    private String name;
    @NotBlank(message = "El campo 'correo' no puede estar en blanco")
    @Email(message = "El campo 'correo' debe ser una dirección de correo electrónico válida")
    private String email;
    @NotBlank(message = "El campo 'password' no puede estar en blanco")
    private String password;
    private List<Phone> phones;

}
