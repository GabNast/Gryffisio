package org.generation.italy.model.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.generation.italy.model.entities.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
