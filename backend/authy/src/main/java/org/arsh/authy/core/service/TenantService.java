package org.arsh.authy.core.service;

import org.arsh.authy.web.dto.TenantRegistrationRequest;
import org.arsh.authy.web.dto.TenantView;

public interface TenantService {
    TenantView registerTenant(TenantRegistrationRequest request);
}
