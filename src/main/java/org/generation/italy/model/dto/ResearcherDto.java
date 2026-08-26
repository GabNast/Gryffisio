package org.generation.italy.model.dto;

public record ResearcherDto (
        Long id,
        String name,
        String surname,
        boolean student,
        boolean active
){}
