package org.arsh.authy.persistence.repository;

import org.arsh.authy.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndTenant_Id(String email, Long tenantId);
    boolean existsByEmailAndTenant_Id(String email, Long tenantId);
}
