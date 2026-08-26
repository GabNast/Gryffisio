package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReferringDoctorRequest(
        @NotBlank
        @Size(max = 20)
        String name,

        @NotBlank
        @Size(max = 20)
        String surname,

        Character gender

) {
}
