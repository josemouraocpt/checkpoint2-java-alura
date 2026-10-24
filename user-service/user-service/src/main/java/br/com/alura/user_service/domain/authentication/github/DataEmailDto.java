package br.com.alura.user_service.domain.authentication.github;

public record DataEmailDto(
    String email,
    Boolean primary,
    Boolean verified,
    String visibility
) {

}
