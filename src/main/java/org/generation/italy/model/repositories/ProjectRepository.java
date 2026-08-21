package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    Optional<Project> findByAcronymIgnoreCase(String acronym);
    boolean existsByAcronymIgnoreCase(String acronym);
}
