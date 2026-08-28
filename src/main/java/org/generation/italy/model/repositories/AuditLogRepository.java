package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId);
    List<AuditLog> findByOperator_Id(Integer operatorId);
}