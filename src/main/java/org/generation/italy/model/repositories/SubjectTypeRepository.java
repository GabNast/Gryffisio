package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectTypeRepository extends JpaRepository<SubjectType, Long> {
    Optional<SubjectType> findByTypeIgnoreCase(String type);
    boolean existsByTypeIgnoreCase(String type);
    boolean existsByTypeIgnoreCaseAndIdNot(String type, Long id);
}
