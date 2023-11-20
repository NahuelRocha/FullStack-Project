package com.rocha.fullstack.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "The user name can not be empty")
    private String email;

    @Size(min = 6, message = "The password must be at least 8 characters")
    @NotBlank(message = "The password can not be empty")
    private String password;

}
