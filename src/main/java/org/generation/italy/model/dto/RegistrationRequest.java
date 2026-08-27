package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record RegistrationRequest(
        @NotNull
        Long domainId,

        @NotEmpty
        @Size(max = 5)
        List<Long> researcherIds,

        LocalDate date,

        @NotNull
        Integer durationMinutes,

        @NotNull
        Long projectId,

        @NotNull
        Long patientId,

        List<Long> additionalPatientIds,

        Long referringDoctorId,

        @NotEmpty
        List<Long> testIds
) {}