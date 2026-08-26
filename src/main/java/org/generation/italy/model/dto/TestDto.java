package org.generation.italy.model.dto;

import org.generation.italy.model.entities.Domain;

import java.util.Set;

//ciao

public record TestDto(
        Long id,
        String name,
        Set<DomainDto> domains
) {}
