package br.com.alura.user_service.domain.user;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter 
@Setter 
@NoArgsConstructor 
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String token;
    private LocalDateTime expirationToken;
    @Enumerated(EnumType.STRING)
    private Profile role;
    private String password;

    public User(RegisterUserDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.role = dto.role();
        this.password = dto.password();
        this.token = UUID.randomUUID().toString();
        this.expirationToken = LocalDateTime.now().plusMinutes(30);
    }

    public void updateData(UpdateUserDto dto) {
        this.name = dto.name();
        this.email = dto.email();
    }
}
