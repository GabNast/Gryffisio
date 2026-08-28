package org.generation.italy.services;

import org.generation.italy.model.dto.AuditLogDto;
import org.generation.italy.model.entities.AuditLog;
import org.generation.italy.model.entities.Operator;
import org.generation.italy.model.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    private AuditLogDto toDto(AuditLog log) {
        return new AuditLogDto(
                log.getId(),
                log.getOperators().stream().map(Operator::getId).toList(),
                log.getOperators().stream().map(o -> o.getFirstName() + " " + o.getLastName()).toList(),
                log.getAction(),
                log.getEntityName(),
                log.getEntityId(),
                log.getDescription(),
                log.getCreatedAt()
        );
    }

    @Transactional
    public void log(Set<Operator> operators, String action, String entityName, Long entityId, String description) {
        AuditLog entry = new AuditLog();
        entry.setOperators(operators);
        entry.setAction(action);
        entry.setEntityName(entityName);
        entry.setEntityId(entityId);
        entry.setDescription(description);
        entry.setCreatedAt(LocalDateTime.now());
        auditLogRepository.save(entry);
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto> findAll() {
        return auditLogRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuditLogDto> findByEntity(String entityName, Long entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId).stream()
                .map(this::toDto)
                .toList();
    }
}