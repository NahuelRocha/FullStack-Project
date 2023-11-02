package com.rocha.fullstack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 12, message = "The name must be between 3 and 12 characters")
    @NotEmpty(message = "This field can not be blank")
    private String firstname;

    @Size(min = 3, max = 12, message = "The lastname must be between 3 and 12 characters")
    @NotEmpty(message = "This field can not be blank")
    private String lastname;

    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email format")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,12}$", message = "Invalid password")
    @NotEmpty
    @Size(min = 6, max = 12, message = "The name must be between 3 and 12 characters")
    private String password;
}
