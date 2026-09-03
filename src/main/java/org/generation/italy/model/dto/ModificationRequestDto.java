package org.generation.italy.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ModificationRequestDto(
        Long id,
        Long registrationId,
        Integer operatorId,
        String operatorFullName,
        LocalDate newActivityDate,
        Integer newDurationMinutes,
        Integer newSessionId,
        Integer newDoctorId,
        String reason,
        String status,
        LocalDateTime submittedAt,
        Integer handledByAdminId,
        LocalDateTime handledAt,
        String rejectionReason
) {}