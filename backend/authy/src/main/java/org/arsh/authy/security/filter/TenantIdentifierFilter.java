package org.arsh.authy.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.arsh.authy.core.context.TenantContext;
import org.arsh.authy.persistence.entity.TenantEntity;
import org.arsh.authy.persistence.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class TenantIdentifierFilter extends OncePerRequestFilter {

    @Value("${app.base-domain}")
    private String baseDomain;

    private final TenantRepository tenantRepository;

    public TenantIdentifierFilter(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String tenantId = resolveTenantIdFromRequest(request);
        if (tenantId == null || tenantId.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<TenantEntity> tenant = tenantRepository.findByTenantId(tenantId);
        if (tenant.isEmpty()){
            log.warn("Attempt to access with invalid tenant identifier: {}", tenantId);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid tenant identifier\"}");
            return;
        }

        try {
            TenantContext.setCurrentTenant(tenant.get().getId());
            filterChain.doFilter(request, response);
        } finally {
            log.info("Clearing TenantContext");
            TenantContext.clean();
        }
    }

    private String resolveTenantIdFromRequest(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (serverName.endsWith("." + baseDomain)) {
            return serverName.substring(0, serverName.length() - (baseDomain.length() + 1));
        }
        log.warn("TenantId not found in sub domain of request server name: {}", serverName);
        return request.getHeader("X-Tenant-ID");
    }
}
