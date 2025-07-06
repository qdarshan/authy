package org.arsh.authy.core.aop;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.arsh.authy.core.context.TenantContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TenantFilterAspect {
    private final EntityManager entityManager;

    public TenantFilterAspect(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Before("execution(* org.arsh.authy.persistence.repository.*.*(..))")
    public void activateTenantFilter() {
        final Session session = entityManager.unwrap(Session.class);
        final Long tenantId = TenantContext.getCurrentTenant();

        if (tenantId != null) {
            log.debug("Activating tenant filter for tenant ID: {}", tenantId);
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
        } else {
            log.warn("Tenant Id not found in TenantContext");
        }
    }

}
