package org.generation.italy.model.dto;

public record PatientDto(
        Long id,
        Integer patientCode,
        Long subjectTypeId,
        String subjectTypeName
) {
}
