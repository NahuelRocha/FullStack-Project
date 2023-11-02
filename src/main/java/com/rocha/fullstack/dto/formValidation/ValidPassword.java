package com.rocha.fullstack.dto.formValidation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ValidPassword(

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,12}$", message = "Invalid password")
        @NotEmpty
        @Size(min = 6, max = 12, message = "The name must be between 3 and 12 characters")
        String password
) {
}
