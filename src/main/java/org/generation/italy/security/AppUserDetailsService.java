package org.generation.italy.security;

import org.generation.italy.model.entities.Admin;
import org.generation.italy.model.repositories.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public AppUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Admin user = adminRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));
        return new AppUserPrincipal(user);
    }
}

