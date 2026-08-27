package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TestRequest(
        @NotBlank
        @Size(max = 128)
        String name,

        List<Long> domainIds
) {}