package org.arsh.authy.web.dto;

import org.arsh.authy.persistence.entity.TenantEntity;

public record TenantView(
        Long id,
        String tenantId,
        String name
) {
    public TenantView(TenantEntity tenant) {
        this(tenant.getId(), tenant.getTenantId(), tenant.getName());
    }
}
