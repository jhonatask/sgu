package br.com.jproject.sgu.application.controller;

import br.com.jproject.sgu.application.dto.response.ResponseTokenDTO;
import br.com.jproject.sgu.application.dto.resquest.LoginRequestDTO;
import br.com.jproject.sgu.core.exceptions.exception.InvalidPasswordException;
import br.com.jproject.sgu.core.exceptions.exception.UserNotFoundException;
import br.com.jproject.sgu.core.security.TokenService;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = userRepository.findByEmail(body.email()).orElseThrow(() -> new UserNotFoundException("Usuario nao encontrado"));
        if(!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new InvalidPasswordException("Senha incorreta");
        }
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new ResponseTokenDTO(token, user.getName()));
    }

}
