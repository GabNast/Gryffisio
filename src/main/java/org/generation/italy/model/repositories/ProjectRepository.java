package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Integer id);

    Optional<Project> findByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCaseAndIdNot(String code, Integer id);
}