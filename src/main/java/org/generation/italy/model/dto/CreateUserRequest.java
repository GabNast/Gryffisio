package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.generation.italy.model.validation.StrongPassword;

import java.util.Set;

public record CreateUserRequest(
        @NotBlank
        @Size(max = 80)
        String name,

        @NotBlank
        @StrongPassword
        String password

        //@NotEmpty
        //Set<@Pattern(regexp = "ADMIN", flags = Pattern.Flag.CASE_INSENSITIVE, message = "must be ADMIN") String> roles
) {}
