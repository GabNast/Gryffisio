package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ModificationRequestRequest(
        @NotNull
        Long registrationId,

        @NotNull
        Integer operatorId,

        LocalDate newActivityDate,
        Integer newDurationMinutes,
        Integer newSessionId,
        Integer newDoctorId,

        @NotBlank
        String reason
) {}