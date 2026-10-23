package br.com.alura.user_service.domain.authentication;

import jakarta.validation.constraints.NotBlank;

public record LoginDataDto(
    @NotBlank
    String email,
    @NotBlank 
    String password
) {

}
