package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 20)
        String code
) {}