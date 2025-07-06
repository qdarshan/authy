package org.arsh.authy.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.arsh.authy.core.service.TenantService;
import org.arsh.authy.persistence.entity.TenantEntity;
import org.arsh.authy.persistence.repository.TenantRepository;
import org.arsh.authy.web.dto.TenantRegistrationRequest;
import org.arsh.authy.web.dto.TenantView;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public TenantView registerTenant(TenantRegistrationRequest request) {
        if (tenantRepository.existsByTenantId(request.tenantId())) {
            throw new IllegalArgumentException("Tenant identifier " + request.tenantId() + " is already taken.");
        }
        TenantEntity tenant = new TenantEntity();
        tenant.setTenantId(request.tenantId());
        tenant.setName(request.name());
        TenantEntity savedTenant = tenantRepository.save(tenant);
        log.info("Registered new tenant: {} (ID: {})", savedTenant.getTenantId(), savedTenant.getId());
        return new TenantView(savedTenant);
    }
}
