package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.generation.italy.model.entities.Domain;

import java.util.Set;

//ciao
public record TestRequest(
        @NotBlank
        @Size(max=128)
        String name,

        Set<Long> domainIds
) {}
