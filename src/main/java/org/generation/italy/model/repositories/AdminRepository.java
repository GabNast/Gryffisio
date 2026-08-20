package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByName(String name);
    boolean existsByName(String name);
}

