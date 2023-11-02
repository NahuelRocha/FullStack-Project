package com.rocha.fullstack.dto.formValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record ValidEmail(
        @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email format")
        @Email(message = "Invalid email format")
        String email
) {
}
