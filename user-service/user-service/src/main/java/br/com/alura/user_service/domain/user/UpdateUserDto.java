package br.com.alura.user_service.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(
    @NotBlank
    String name, 
    @NotBlank
    @Email
    String email
) {

}
