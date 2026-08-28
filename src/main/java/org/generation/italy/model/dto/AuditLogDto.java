package org.generation.italy.model.dto;

import java.time.LocalDateTime;

public record AuditLogDto(
        Long id,
        Integer operatorId,
        String operatorFullName,
        String action,
        String entityName,
        Long entityId,
        String description,
        LocalDateTime createdAt
) {}