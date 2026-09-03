package org.generation.italy.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.generation.italy.model.validation.StrongPassword;

public record CreateUserRequest(
        @NotBlank
        @Size(max = 50)
        String firstName,

        @NotBlank
        @Size(max = 50)
        String lastName,

        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @StrongPassword
        String password,

        @NotBlank
        String role
) {}