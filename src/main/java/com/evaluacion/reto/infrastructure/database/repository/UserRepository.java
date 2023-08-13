package com.evaluacion.reto.infrastructure.database.repository;

import com.evaluacion.reto.domain.port.UserPersistencePort;
import com.evaluacion.reto.domain.User;
import com.evaluacion.reto.infrastructure.config.UserException;
import com.evaluacion.reto.infrastructure.database.entity.UserEntity;
import com.evaluacion.reto.infrastructure.database.mapper.UserEntityMapper;
import com.evaluacion.reto.domain.UserRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import io.jsonwebtoken.Jwts;


import java.security.SecureRandom;
import java.util.Base64;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserRepository implements UserPersistencePort {
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${pattern.pasword}")
    private String patternPassword;
    @Value("${pasword.msg}")
    private String mesagePassword;


    @Override
    public Mono<User> saveUser(UserRequest userRequest) {

        if (validatePassword(userRequest.getPassword())) {
            return Mono.error( UserException.builder().statusCode(400).description(mesagePassword).build());
        }

        // create metodo para validar si el usuario ya existe
        return userExist(userRequest.getEmail()).flatMap(userExist -> {
            if (userExist) {
                return Mono.error(UserException.builder().statusCode(409).description("User already exists").build());
            } else {
                return userJpaRepository.save(userEntityMapper.toEntity(userRequest)
                        .withPassword(passwordEncoder.encode(userRequest.getPassword()))
                                .withLastLogin(java.time.LocalDateTime.now()))
                        .map(userEntity -> userEntityMapper.toDomain(userEntity).withToken(generateToken(userEntity)))
                        .doOnSuccess(user -> log.info("User saved id {}" , user.getId()))
                        .subscribeOn(Schedulers.boundedElastic());
            }
        });

    }

    private Mono<Boolean> userExist(String email) {
        return userJpaRepository.findByEmail(email).map(userEntity -> {
            log.info("User already exists");
            return true;
        }).switchIfEmpty(Mono.just(false));
    }

    private boolean validatePassword(String password) {
        return !password.matches(patternPassword);
    }



    @Override
    public Mono<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userEntity -> userEntityMapper.toDomain(userEntity));
    }

    @Override
    public Mono<User> login(String email, String password) {
        return userJpaRepository.findByEmail(email).map(
                userEntity -> {
                    if (passwordEncoder.matches(password, userEntity.getPassword())) {
                        userEntity.setLastLogin(java.time.LocalDateTime.now());
                        userJpaRepository.save(userEntity)
                                .subscribeOn(Schedulers.boundedElastic())
                                .doOnSuccess(user -> log.info("lastlogin actualizado  id = {} , lastlogin = {}" , user.getId(), user.getLastLogin()));
                            return userEntityMapper.toDomain(userEntity)
                                    .withToken(generateToken(userEntity));

                    } else {
                        log.info("password incorrecto");
                    }
                    return null;
                }
        ).switchIfEmpty(Mono.error(UserException.builder().statusCode(404).description("User not found").build()));

    }



    public String generateToken(UserEntity u)  {
        Claims claims = Jwts.claims().setSubject(u.getName());
        claims.put("userId", u.getId() + "");
        claims.put("name", u.getName());
        claims.put("scope", "admins");
        claims.put("email", u.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, generateSecretKey())
                .compact();
    }

    private static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[512];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }


}
