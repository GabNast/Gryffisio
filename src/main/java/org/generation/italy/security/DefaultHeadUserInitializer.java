package org.generation.italy.security;

import org.generation.italy.model.entities.Operator;
import org.generation.italy.model.repositories.OperatorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultHeadUserInitializer implements CommandLineRunner {
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultHeadUserInitializer(OperatorRepository operatorRepository, PasswordEncoder passwordEncoder) {
        this.operatorRepository = operatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Read env vars. Do NOT default the password to an insecure value.
        String email = System.getenv().getOrDefault("APP_HEAD_EMAIL", "head@example.com");
        String password = System.getenv().get("APP_HEAD_PASSWORD"); // intentionally no default

        if (operatorRepository.existsByEmailIgnoreCase(email)) {
            return;
        }

        // If password is not provided or is obviously insecure, skip automatic creation and warn.
        if (password == null || password.isBlank()) {
            System.err.println("[WARN] APP_HEAD_PASSWORD not set — skipping creation of default HEAD user. " +
                    "To create a HEAD user at startup set APP_HEAD_EMAIL and APP_HEAD_PASSWORD environment variables.");
            return;
        }

        if ("head".equals(password) || "password".equalsIgnoreCase(password)) {
            System.err.println("[WARN] APP_HEAD_PASSWORD is insecure (uses a common default) — skipping creation of default HEAD user. " +
                    "Use a strong random password via APP_HEAD_PASSWORD.");
            return;
        }

        Operator operator = new Operator();
        operator.setFirstName("Head");
        operator.setLastName("Admin");
        operator.setEmail(email);
        operator.setRole(Operator.Role.ADMIN);
        operator.setPasswordHash(passwordEncoder.encode(password));
        operatorRepository.save(operator);
    }
}