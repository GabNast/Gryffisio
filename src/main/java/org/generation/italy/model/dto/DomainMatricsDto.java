package org.generation.italy.model.dto;

public record DomainMatricsDto(
        Integer domainId,
        String domainName,
        long registrationCount,
        double totalHours
) {}
