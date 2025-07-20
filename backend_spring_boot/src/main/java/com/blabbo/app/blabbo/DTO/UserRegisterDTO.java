package com.blabbo.app.blabbo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
    @Email(message = "Valid email must be provided")
    @NotNull(message = "Email must be provided")
    @NotEmpty(message = "Email cannot be empty")
    private final String email;
    @NotNull(message = "Name must be provided")
    @NotEmpty(message = "Name cannot be empty")
    private final String name;
    @NotNull(message = "Password must be provided")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters long")
    private final String password;


    public UserRegisterDTO(String email, String name, String rawPassword) {
        this.email = email;
        this.name = name;
        this.password = rawPassword;
    }


    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }

}
