package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.RegistrationRequestModification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRequestModificationRepository extends JpaRepository<RegistrationRequestModification, Long> {
    List<RegistrationRequestModification> findByState(Character state);
}
