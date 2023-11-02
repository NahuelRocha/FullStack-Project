package com.rocha.fullstack.dto.formValidation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ValidName(
        @Size(min = 3, max = 12, message = "The name must be between 3 and 12 characters")
        @NotEmpty(message = "This field can not be blank")
        String firstname
) {
}
