package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import edu.pjatk.tin.restaurant.infrastructure.web.constraint_validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank
        @Size(min = 1, max = 50)
        String firstName,
        @NotBlank
        @Size(min = 1, max = 50)
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @ValidPassword
        String password
) {
}
