package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId);

    @Query("SELECT a FROM AuditLog a JOIN a.operators o WHERE o.id = :operatorId")
    List<AuditLog> findByOperatorId(Integer operatorId);
}