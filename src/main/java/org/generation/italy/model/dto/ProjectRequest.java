package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
//ciao
public record ProjectRequest(
        @NotBlank
        @Size(max = 255)
        String name,

        @NotBlank
        @Size(max=5)
        String acronym
) {}
