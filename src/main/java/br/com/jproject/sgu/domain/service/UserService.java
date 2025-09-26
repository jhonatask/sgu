package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.core.exceptions.exception.CpfAlreadyRegisteredException;
import br.com.jproject.sgu.core.exceptions.exception.EmailAlreadyRegisteredException;
import br.com.jproject.sgu.core.exceptions.exception.UserNotFoundException;
import br.com.jproject.sgu.core.constants.ErrorMessages;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.domain.model.Department;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapperDTO userResponseMapperDTO;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, UserResponseMapperDTO userResponseMapperDTO, DepartmentService departmentService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userResponseMapperDTO = userResponseMapperDTO;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = {"users", "user-by-email", "user-by-cpf", "users-by-name", "users-by-department", "users-by-date-range"}, allEntries = true)
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Validar CPF/CNPJ duplicado
        Optional<User> existingUser = userRepository.findBycpforcnpj(userRequestDTO.getCpforcnpj());
        if (existingUser.isPresent()){
            throw new CpfAlreadyRegisteredException();
        }
        
        // Validar email duplicado
        Optional<User> existingEmail = userRepository.findByEmail(userRequestDTO.getEmail());
        if (existingEmail.isPresent()){
            throw new EmailAlreadyRegisteredException();
        }
        
        User newUser = builderNewUser(userRequestDTO);
        return userResponseMapperDTO.userToUserResponseDTO(newUser);
    }


    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        return result.map(userResponseMapperDTO::userToUserResponseDTO);
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userResponseMapperDTO.userToUserResponseDTO(user);
    }

    public List<UserResponseDTO> getUserByNome(String nome) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(nome);
        return users.stream().map(userResponseMapperDTO::userToUserResponseDTO).toList();
    }

    @CacheEvict(value = {"users", "user-by-email", "user-by-cpf", "users-by-name", "users-by-department", "users-by-date-range"}, allEntries = true)
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userDetails) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        
        // Validar CPF/CNPJ duplicado (exceto para o próprio usuário)
        Optional<User> existingUser = userRepository.findBycpforcnpj(userDetails.getCpforcnpj());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)){
            throw new CpfAlreadyRegisteredException(ErrorMessages.CPF_ALREADY_REGISTERED_UPDATE);
        }
        
        // Validar email duplicado (exceto para o próprio usuário)
        Optional<User> existingEmail = userRepository.findByEmail(userDetails.getEmail());
        if (existingEmail.isPresent() && !existingEmail.get().getId().equals(id)){
            throw new EmailAlreadyRegisteredException(ErrorMessages.EMAIL_ALREADY_REGISTERED_UPDATE);
        }
        
        setDataUser(userDetails, user);
        return userResponseMapperDTO.userToUserResponseDTO(user);
    }

    @CacheEvict(value = {"users", "user-by-email", "user-by-cpf", "users-by-name", "users-by-department", "users-by-date-range"}, allEntries = true)
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    private User builderNewUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setDatacadastro(LocalDateTime.now());
        setDataUser(userRequestDTO, user);
        return user;
    }

    @CacheEvict(value = {"users", "user-by-email", "user-by-cpf", "users-by-name", "users-by-department", "users-by-date-range"}, allEntries = true)
    private void setDataUser(UserRequestDTO userRequestDTO, User user) {
        Department department = departmentService.getDepartment(userRequestDTO.getDepartment());
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail()); // Usa o setter que aceita String
        if(!(userRequestDTO.getPassword() == null)) user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setTelefone(userRequestDTO.getTelefone()); // Usa o setter que aceita String
        user.setCpforcnpj(userRequestDTO.getCpforcnpj());
        user.setDepartment(department);
        user.setDataalteracao(LocalDateTime.now());
        userRepository.save(user);
    }
}
