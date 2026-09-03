package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SessionRequest(
        @NotBlank
        @Size(max = 50)
        String session
) {}