package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SessionTypeCategoryRequest(
        @NotBlank
        @Size(max = 255)
        String name
) {
}
