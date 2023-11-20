package com.rocha.fullstack.dto.formValidation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ValidPassword(

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$", message = "Password must have at least 6 characters, an uppercase letter, a number, and a special character")
        @NotEmpty
        @Size(min = 6, message = "The password must be at least 6 characters, max 20 characters")
        String password
) {
}
