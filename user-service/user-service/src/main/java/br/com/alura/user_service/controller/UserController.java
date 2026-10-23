package br.com.alura.user_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.user_service.domain.user.RegisterUserDto;
import br.com.alura.user_service.domain.user.UpdateUserDto;
import br.com.alura.user_service.domain.user.UserDto;
import br.com.alura.user_service.domain.user.UserService;
import br.com.alura.user_service.infra.exception.ValidationException;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<UserDto>> list(){
        List<UserDto> usuarios = service.listAllUsers();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> find(@PathVariable String id){
        try {
            UserDto usuario = service.findUserById(id);
            return ResponseEntity.ok(usuario);
        } catch (ValidationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid RegisterUserDto dto){
        try {
            service.registerUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid UpdateUserDto dto){
        try {
            service.updateUser(dto, id);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
