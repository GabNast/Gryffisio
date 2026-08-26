package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//ciao
public record DomainRequest(
        @NotBlank
        @Size(max = 50)
        String name
) {}
