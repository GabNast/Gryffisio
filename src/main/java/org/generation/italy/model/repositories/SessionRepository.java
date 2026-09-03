package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findBySessionIgnoreCase(String session);
    boolean existsBySessionIgnoreCase(String session);
    boolean existsBySessionIgnoreCaseAndIdNot(String session, Integer id);
}