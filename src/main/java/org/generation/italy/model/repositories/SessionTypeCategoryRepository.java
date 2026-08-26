package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.SessionType;
import org.generation.italy.model.entities.SessionTypeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionTypeCategoryRepository extends JpaRepository<SessionTypeCategory, Integer> {
    Optional<SessionTypeCategory> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Integer id);
}
