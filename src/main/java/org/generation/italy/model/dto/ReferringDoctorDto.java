package org.generation.italy.model.dto;

public record ReferringDoctorDto(
        Long id,
        String name,
        String surname,
        Character gender
) {
}
