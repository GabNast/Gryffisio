package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByProject_IdAndCodeIgnoreCase(Integer projectId, String code);
    boolean existsByProject_IdAndCodeIgnoreCase(Integer projectId, String code);
    boolean existsByProject_IdAndCodeIgnoreCaseAndIdNot(Integer projectId, String code, Long id);

    List<Subject> findByProject_Id(Integer projectId);
}