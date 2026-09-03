package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DomainRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        List<Integer> activityIds
) {}