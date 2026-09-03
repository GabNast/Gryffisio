package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.ModificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModificationRequestRepository extends JpaRepository<ModificationRequest, Long> {
    List<ModificationRequest> findByStatus(ModificationRequest.Status status);
    List<ModificationRequest> findByRegistration_Id(Long registrationId);
}