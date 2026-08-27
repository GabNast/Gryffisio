package org.generation.italy.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record RegistrationDto(
        Long id,
        Long domainId,
        String domainName,
        LocalDate date,
        Integer durationMinutes,
        Long projectId,
        String projectName,
        Long patientId,
        Integer patientCode,
        List<Long> additionalPatientIds,
        List<Long> researcherIds,
        Long referringDoctorId,
        String referringDoctorName,
        List<Long> testIds,
        LocalDateTime creationDate,
        Boolean modified
) {}