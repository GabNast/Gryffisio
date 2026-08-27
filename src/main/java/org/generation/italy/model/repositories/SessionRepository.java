package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByEvaluationIgnoreCase(String evaluation);
    boolean existsByEvaluationIgnoreCase(String evaluation);
    boolean existsByEvaluationIgnoreCaseAndIdNot(String evaluation, Integer id);
}