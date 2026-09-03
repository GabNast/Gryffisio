package org.generation.italy.model.dto;

public record OperatorMatricsDto(
        Integer operatorId,
        String operatorName,
        long registrationCount,
        double totalHours
    ) {}
