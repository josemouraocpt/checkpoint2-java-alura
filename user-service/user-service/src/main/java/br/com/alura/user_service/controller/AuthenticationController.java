package br.com.alura.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.user_service.domain.authentication.LoginDataDto;
import br.com.alura.user_service.domain.authentication.TokenDto;
import br.com.alura.user_service.domain.authentication.TokenService;
import br.com.alura.user_service.domain.user.User;
import jakarta.validation.Valid;

@RestController 
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> handleLogin(@Valid @RequestBody LoginDataDto dto){
        var authUser = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(authUser);
        String accessToken = tokenService.generateTokenJWT((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDto(accessToken));
    }
}
