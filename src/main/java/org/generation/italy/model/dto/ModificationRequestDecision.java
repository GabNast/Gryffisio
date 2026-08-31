package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotNull;

public record ModificationRequestDecision(
        @NotNull
        Boolean approve,
        String rejectionReason
) {}