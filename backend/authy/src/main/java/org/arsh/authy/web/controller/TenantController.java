package org.arsh.authy.web.controller;

import jakarta.validation.Valid;
import org.arsh.authy.core.service.TenantService;
import org.arsh.authy.web.dto.TenantRegistrationRequest;
import org.arsh.authy.web.dto.TenantView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants/")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/register")
    public ResponseEntity<TenantView> registerTenant(
            @Valid @RequestBody TenantRegistrationRequest request
    ) {
        TenantView tenant = tenantService.registerTenant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tenant);
    }

}
