package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DomainRequest(
        @NotBlank
        @Size(max = 50)
        String name
) {}
