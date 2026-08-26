package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SessionRequest(
        @NotBlank
        @Size(max = 20)
        String evaluation,

        @NotNull
        Integer sessionTypeId
) {}
