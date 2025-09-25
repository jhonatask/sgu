package br.com.jproject.sgu.application.usecase;

import br.com.jproject.sgu.application.dto.request.UserRequestDTO;
import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.core.exceptions.exception.CpfAlreadyRegisteredException;
import br.com.jproject.sgu.core.exceptions.exception.EmailAlreadyRegisteredException;
import br.com.jproject.sgu.core.exceptions.exception.UserNotFoundException;
import br.com.jproject.sgu.core.constants.ErrorMessages;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repository.UserRepository;
import br.com.jproject.sgu.domain.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final DepartmentService departmentService;
    private final UserResponseMapperDTO userResponseMapperDTO;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(UUID id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.format(ErrorMessages.USER_NOT_FOUND_WITH_ID, id)));
        
        validateUserData(userRequestDTO, id);
        updateUserData(user, userRequestDTO);
        
        User savedUser = userRepository.save(user);
        return userResponseMapperDTO.userToUserResponseDTO(savedUser);
    }

    private void validateUserData(UserRequestDTO userRequestDTO, UUID currentUserId) {
        // Validar CPF/CNPJ duplicado (exceto para o próprio usuário)
        Optional<User> existingUser = userRepository.findBycpforcnpj(userRequestDTO.getCpforcnpj());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(currentUserId)) {
            throw new CpfAlreadyRegisteredException(ErrorMessages.CPF_ALREADY_REGISTERED_UPDATE);
        }
        
        // Validar email duplicado (exceto para o próprio usuário)
        Optional<User> existingEmail = userRepository.findByEmail(userRequestDTO.getEmail());
        if (existingEmail.isPresent() && !existingEmail.get().getId().equals(currentUserId)) {
            throw new EmailAlreadyRegisteredException(ErrorMessages.EMAIL_ALREADY_REGISTERED_UPDATE);
        }
    }

    private void updateUserData(User user, UserRequestDTO userRequestDTO) {
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setTelefone(userRequestDTO.getTelefone());
        user.setCpforcnpj(userRequestDTO.getCpforcnpj());
        
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        
        user.setDepartment(departmentService.getDepartment(userRequestDTO.getDepartment()));
    }
}
