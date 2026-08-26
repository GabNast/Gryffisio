package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectTypeRequest (
        @NotBlank
        @Size(max = 50)
        String type
){}

