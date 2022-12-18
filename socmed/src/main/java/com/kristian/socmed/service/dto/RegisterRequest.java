package com.kristian.socmed.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email(message = "Bad email format")
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;
    
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    private String username;
    
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;
}