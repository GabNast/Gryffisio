package org.generation.italy.security;

import org.generation.italy.model.entities.Admin;
import org.generation.italy.model.repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultHeadUserInitializer implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultHeadUserInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Read env vars. Do NOT default the password to an insecure value.
        String username = System.getenv().getOrDefault("APP_HEAD_USERNAME", "head");
        String password = System.getenv().get("APP_HEAD_PASSWORD"); // intentionally no default

        if (adminRepository.existsByName(username)) {
            return;
        }

        // If password is not provided or is obviously insecure, skip automatic creation and warn.
        if (password == null || password.isBlank()) {
            System.err.println("[WARN] APP_HEAD_PASSWORD not set — skipping creation of default HEAD user. " +
                    "To create a HEAD user at startup set APP_HEAD_USERNAME and APP_HEAD_PASSWORD environment variables.");
            return;
        }

        if ("head".equals(password) || "password".equalsIgnoreCase(password)) {
            System.err.println("[WARN] APP_HEAD_PASSWORD is insecure (uses a common default) — skipping creation of default HEAD user. " +
                    "Use a strong random password via APP_HEAD_PASSWORD.");
            return;
        }

        Admin user = new Admin();
        user.setName(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        adminRepository.save(user);
    }
}
