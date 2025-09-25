package br.com.jproject.sgu.application.usecase;


import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.core.exceptions.exception.CpfAlreadyRegisteredException;
import br.com.jproject.sgu.core.exceptions.exception.EmailAlreadyRegisteredException;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repositories.UserRepository;
import br.com.jproject.sgu.domain.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final DepartmentService departmentService;
    private final UserResponseMapperDTO userResponseMapperDTO;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(UserRequestDTO userRequestDTO) {
        validateUserData(userRequestDTO);
        
        User newUser = buildNewUser(userRequestDTO);
        User savedUser = userRepository.save(newUser);
        
        return userResponseMapperDTO.userToUserResponseDTO(savedUser);
    }

    private void validateUserData(UserRequestDTO userRequestDTO) {
        // Validar CPF/CNPJ duplicado
        Optional<User> existingUser = userRepository.findBycpforcnpj(userRequestDTO.getCpforcnpj());
        if (existingUser.isPresent()) {
            throw new CpfAlreadyRegisteredException();
        }
        
        // Validar email duplicado
        Optional<User> existingEmail = userRepository.findByEmail(userRequestDTO.getEmail());
        if (existingEmail.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
    }

    private User buildNewUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail()); // Usa o setter que aceita String
        user.setTelefone(userRequestDTO.getTelefone()); // Usa o setter que aceita String
        user.setCpforcnpj(userRequestDTO.getCpforcnpj());
        user.setDatacadastro(LocalDateTime.now());
        
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        
        user.setDepartment(departmentService.getDepartment(userRequestDTO.getDepartment()));
        user.setDataalteracao(LocalDateTime.now());
        
        return user;
    }
}
