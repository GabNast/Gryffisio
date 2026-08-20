package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String name,

        @NotBlank
        String password
) {}
