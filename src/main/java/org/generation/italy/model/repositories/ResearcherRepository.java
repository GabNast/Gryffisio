package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResearcherRepository extends JpaRepository<Researcher, Long> {
    Optional<Researcher> findByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
    List<Researcher> findByIsActiveTrue();
    List<Researcher> findByIsActiveFalse();

    boolean existsByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
    boolean existsByNameIgnoreCaseAndSurnameIgnoreCaseAndIdNot(String name, String surname, Long id);
}
