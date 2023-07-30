package com.evaluacion.reto.infrastructure.database.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("users")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private UUID id;
    private String name;
    private String email;
    @With
    private String password;
    @Column("active")
    private Boolean isActive;
    private LocalDateTime created;
    private LocalDateTime modified;
    @Column("last_login")
    @With
    private LocalDateTime lastLogin;
}
