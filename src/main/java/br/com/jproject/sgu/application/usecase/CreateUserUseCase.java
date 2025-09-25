package br.com.jproject.sgu.application.usecase;

import br.com.jproject.sgu.application.dto.request.UserRequestDTO;
import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repository.UserRepository;
import br.com.jproject.sgu.domain.service.DepartmentService;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.core.exceptions.exception.CpfAlreadyRegisteredException;
import br.com.jproject.sgu.core.exceptions.exception.EmailAlreadyRegisteredException;
import br.com.jproject.sgu.core.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        user.setEmail(userRequestDTO.getEmail());
        user.setTelefone(userRequestDTO.getTelefone());
        user.setCpforcnpj(userRequestDTO.getCpforcnpj());
        user.setDatacadastro(new Date());
        
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        
        user.setDepartment(departmentService.getDepartment(userRequestDTO.getDepartment()));
        
        return user;
    }
}
