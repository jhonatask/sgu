package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.core.exceptions.exception.UserNotFoundException;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.domain.model.Department;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapperDTO userResponseMapperDTO;
    private final DepartmentService departmentService;

    public UserService(UserRepository userRepository, UserResponseMapperDTO userResponseMapperDTO, DepartmentService departmentService) {
        this.userRepository = userRepository;
        this.userResponseMapperDTO = userResponseMapperDTO;
        this.departmentService = departmentService;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Optional<User> user = userRepository.findBycpforcnpj(userRequestDTO.cpfOrCnpj);
        if (user.isPresent()){
            throw new RuntimeException("Náo e possivel cadastrar usuario com mesmo cpf");
        }
        User newUser = builderNewUser(userRequestDTO);
        return  userResponseMapperDTO.userToUserResponseDTO(newUser);

    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        return result.map(userResponseMapperDTO::userToUserResponseDTO);
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userResponseMapperDTO.userToUserResponseDTO(user);
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO userDetails) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        setDataUser(userDetails, user);
        return userResponseMapperDTO.userToUserResponseDTO(user);
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    private User builderNewUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setDatacadastro(new Date());
        setDataUser(userRequestDTO, user);
        return user;
    }

    private void setDataUser(UserRequestDTO userRequestDTO, User user) {
        Department department = departmentService.getDepartment(userRequestDTO.department_id);
        user.setName(userRequestDTO.name);
        user.setEmail(userRequestDTO.email);
        user.setPassword(userRequestDTO.password);
        user.setTelefone(userRequestDTO.telefone);
        user.setCpforcnpj(userRequestDTO.cpfOrCnpj);
        user.setDepartment(department);
        userRepository.save(user);
    }
}
