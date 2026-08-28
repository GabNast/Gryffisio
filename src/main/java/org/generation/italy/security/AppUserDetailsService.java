package org.generation.italy.security;

import org.generation.italy.model.entities.Operator;
import org.generation.italy.model.repositories.OperatorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final OperatorRepository operatorRepository;

    public AppUserDetailsService(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Operator operator = operatorRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Operator not found: " + email));
        return new AppUserPrincipal(operator);
    }
}