package br.com.alura.user_service.domain.user;

public record UserDto(
    Long id, 
    String name, 
    String email,
    Profile role
) {
    public UserDto(User user){
            this(user.getId(), user.getName(), user.getEmail(), user.getRole());
        }
}
