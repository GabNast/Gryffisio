package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubjectRequest(
        @NotNull
        Integer projectId,

        @NotBlank
        @Size(max = 50)
        String code,

        @NotNull
        Integer subjectTypeId
) {}