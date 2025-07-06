package org.arsh.authy.security.service.impl;

import org.arsh.authy.core.context.TenantContext;
import org.arsh.authy.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {
        Long tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            throw new UsernameNotFoundException("Tenant context not available for user lookup.");
        }

        return userRepository.findByEmailAndTenant_Id(username, tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}