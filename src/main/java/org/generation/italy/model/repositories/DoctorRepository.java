package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    List<Doctor> findByLastNameIgnoreCase(String lastName);
}