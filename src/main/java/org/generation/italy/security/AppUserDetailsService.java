package org.generation.italy.security;

import org.generation.italy.model.entities.Admin;
import org.generation.italy.model.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Admin user = appUserRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));
        return new AppUserPrincipal(user);
    }
}

