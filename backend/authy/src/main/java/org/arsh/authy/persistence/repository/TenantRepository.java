package org.arsh.authy.persistence.repository;

import org.arsh.authy.persistence.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    Optional<TenantEntity> findByTenantId(String tenantId);
    boolean existsByTenantId(String tenantId);
}
