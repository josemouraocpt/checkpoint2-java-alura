package br.com.alura.user_service.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDto(
    @NotBlank
    String name, 
    @NotBlank
    @Email
    String email,
    @NotBlank
    String password,
    @NotNull 
    Profile role
) {

}
