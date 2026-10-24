package br.com.alura.user_service.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.user_service.domain.authentication.TokenDto;
import br.com.alura.user_service.domain.authentication.TokenService;
import br.com.alura.user_service.domain.authentication.github.LoginGitHubService;
import br.com.alura.user_service.domain.user.User;
import br.com.alura.user_service.domain.user.UserRepository;

@RestController
@RequestMapping("/login/github")
public class AuthenticationGitHubController {

    private final LoginGitHubService loginGitHubService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationGitHubController(LoginGitHubService loginGitHubService, UserRepository userRepository, TokenService tokenService) {
        this.loginGitHubService = loginGitHubService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @GetMapping 
    public ResponseEntity<Void> handleLoginGitHub(){
        var headers = new HttpHeaders();
        headers.setLocation(URI.create(loginGitHubService.generateUrl()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/authorized")
    public ResponseEntity<TokenDto> authenticateUserOauth(@RequestParam String code){
        var email = loginGitHubService.getEmail(code);
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow();

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenService.generateTokenJWT((User) authentication.getPrincipal());
        
        return ResponseEntity.ok(new TokenDto(accessToken));
    }
}
