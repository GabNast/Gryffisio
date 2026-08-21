package org.generation.italy.model.dto;

import org.generation.italy.model.entities.Domain;

import java.util.Set;

public record TestDto(
        Long id,
        String name,
        Set<DomainDto> domains
) {}
