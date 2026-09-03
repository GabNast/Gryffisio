package org.generation.italy.model.dto;

public record OperatorDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String role
) {}