package com.rocha.fullstack.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotNull
    @Min(4)
    private Long amount;

    @Email
    private String email;

}
