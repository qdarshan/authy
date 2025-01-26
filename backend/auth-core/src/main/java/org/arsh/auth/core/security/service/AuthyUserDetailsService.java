package org.arsh.auth.core.security.service;

import org.arsh.auth.core.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthyUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public AuthyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByEmail(username);
        if(userDetails == null){
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }
}
