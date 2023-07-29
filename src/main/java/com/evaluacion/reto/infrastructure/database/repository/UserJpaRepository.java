package com.evaluacion.reto.infrastructure.database.repository;




import com.evaluacion.reto.infrastructure.database.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface UserJpaRepository extends ReactiveCrudRepository<UserEntity, UUID> {
}
