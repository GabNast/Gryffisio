package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.ReferringDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferringDoctorRepository extends JpaRepository<ReferringDoctor,Long> {
    Optional<ReferringDoctor> findByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
    boolean existsByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
    boolean existsByNameIgnoreCaseAndSurnameIgnoreCaseAndIdNot(String name, String surname, Long id);
}
