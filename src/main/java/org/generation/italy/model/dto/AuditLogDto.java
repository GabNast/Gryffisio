package org.generation.italy.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AuditLogDto(
        Long id,
        List<Integer> operatorIds,
        List<String> operatorNames,
        String action,
        String entityName,
        Long entityId,
        String description,
        LocalDateTime createdAt
) {}