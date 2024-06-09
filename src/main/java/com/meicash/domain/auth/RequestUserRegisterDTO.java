package com.meicash.domain.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestUserRegisterDTO(
        @Email(message = "The email format is invalid")
        String email,
        @NotBlank(message = "Username cannot be blank")
        String username,
        @Size(min = MINIMUM_PASSWORD_LENGTH, message = "Password must be at least" + MINIMUM_PASSWORD_LENGTH + "characters long")
        String password,
        @NotBlank(message = "First name cannot be blank")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        String lastName,
        @NotBlank(message = "Company name cannot be blank")
        String companyName
) {
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
}
