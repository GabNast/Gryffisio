package org.generation.italy.model.dto;

import java.time.LocalDate;

public record RegistrationDto(
        Long id ,
        LocalDate date,
        Integer durationMinutes
) {}
