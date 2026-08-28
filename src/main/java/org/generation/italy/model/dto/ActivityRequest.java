package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivityRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        Integer parentId
) {}