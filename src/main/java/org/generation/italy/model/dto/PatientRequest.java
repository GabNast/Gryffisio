package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientRequest(
        @NotNull
        Integer patientCode,

        @NotNull
        Long subjectTypeId
) {
}
