package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @NotBlank
        @Size(min = 1, max = 50)
        String firstName,
        @NotBlank
        @Size(min = 1, max = 50)
        String lastName,
        @NotBlank
        @Email
        String email,
        String password
) {
}
