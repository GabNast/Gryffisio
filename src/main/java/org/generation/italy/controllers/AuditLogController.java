package org.generation.italy.controllers;

import org.generation.italy.model.dto.AuditLogDto;
import org.generation.italy.services.AuditLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLogDto> getAll() {
        return auditLogService.findAll();
    }

    @GetMapping("/by-entity")
    public List<AuditLogDto> getByEntity(@RequestParam String entityName, @RequestParam Long entityId) {
        return auditLogService.findByEntity(entityName, entityId);
    }
}