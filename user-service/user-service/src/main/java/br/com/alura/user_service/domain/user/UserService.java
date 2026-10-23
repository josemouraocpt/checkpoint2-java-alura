package br.com.alura.user_service.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.user_service.infra.exception.ValidationException;
import jakarta.transaction.Transactional;

@Service 
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public List<UserDto> listAllUsers(){
        return repository.findAll().stream().map(UserDto::new).toList();
    }

    @Transactional
    public UserDto findUserById(String id){
        User user = loadUser(id);
        return new UserDto(user);
    }

    @Transactional
    public void registerUser(RegisterUserDto dto){
        boolean alreadyRegistred = repository.existsByEmail(dto.email());
        if (alreadyRegistred){
            throw new ValidationException("E-mail já cadastrado para outro usuário!");
        }
        String encodedPassword = encoder.encode(dto.password());
        repository.save(new User(dto, encodedPassword));
    }

    @Transactional
    public void updateUser(UpdateUserDto dto, Long id){
        User user = repository.getReferenceById(id);
        user.updateData(dto);
        repository.save(user);
    }

    private User loadUser(String id){
        Optional<User> optional;
        try {
            Long idLong = Long.parseLong(id);
            optional = repository.findById(idLong);
        } catch (NumberFormatException e) {
            throw new ValidationException("O id fornecido não é válido!");
        }
        return optional.orElseThrow(() -> new ValidationException("Usuário não encontrado!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }
}
