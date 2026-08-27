package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Test> findByDomains_Id(Long domainId);
}