package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByProjectId(Long projectId);
}
