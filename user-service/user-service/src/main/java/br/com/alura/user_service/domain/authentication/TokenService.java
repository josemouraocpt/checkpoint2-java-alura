package br.com.alura.user_service.domain.authentication;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.alura.user_service.domain.user.User;
import jakarta.validation.ValidationException;

@Service 
public class TokenService {

    public String generateTokenJWT(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345678");
            List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return  JWT.create()
                .withIssuer("user-service")
                .withSubject(user.getUsername())
                .withClaim("roles", roles)
                .withExpiresAt(expiration(30))
                .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new ValidationException("Erro ao gerar token JWT de acesso!");
        }
    }

    public String verifyTokenJWT(String token){
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345678");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("user-service")
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception){
            throw new ValidationException("Erro ao verificar token JWT de acesso!");
        }
    }

    private Instant expiration(Integer minutos) {
        return LocalDateTime.now().plusMinutes(minutos).toInstant(ZoneOffset.of("-03:00"));
    }
}
