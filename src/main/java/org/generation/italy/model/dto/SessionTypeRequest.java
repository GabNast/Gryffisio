package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SessionTypeRequest(
        @NotBlank
        @Size(max = 20)
        String name,

        @NotBlank
        @Size(max = 20)
        String code,

        @NotNull
        Integer sessionTypeCategoryId
) {
}
