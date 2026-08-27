package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientCode(Integer patientCode);
    boolean existsByPatientCode(Integer patientCode);
    boolean existsByPatientCodeAndIdNot(Integer patientCode, Long id);
}
