package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.generation.italy.model.validation.StrongPassword;

public record ResetPasswordRequest(
        @NotBlank
        @StrongPassword
        String newPassword
) {}
