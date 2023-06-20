package com.yostoya.shoptill.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(

        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
