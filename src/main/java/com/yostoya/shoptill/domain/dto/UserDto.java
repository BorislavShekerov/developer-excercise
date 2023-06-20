package com.yostoya.shoptill.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yostoya.shoptill.domain.Role;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public record UserDto(
        Long id,

        @NotBlank(message = "First name cannot be empty")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        @NotBlank(message = "Email cannot be empty")
        String email,

        @NonNull
        String role,

        @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdOn
) {

}
