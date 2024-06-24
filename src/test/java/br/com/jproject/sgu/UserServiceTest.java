package br.com.jproject.sgu;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.domain.model.Department;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repositories.UserRepository;
import br.com.jproject.sgu.domain.service.DepartmentService;
import br.com.jproject.sgu.domain.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserResponseMapperDTO userResponseMapperDTO;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setName("John Doe");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("John Doe");
        userRequestDTO.setCpforcnpj("12345678900");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);
        userResponseDTO.setName("John Doe");
    }

    @Test
    public void quandoCriarUsuario_entaoUsuarioEAtualizado() {
        when(userRepository.findBycpforcnpj(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userResponseMapperDTO.userToUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        assertThat(createdUser.getName()).isSameAs(user.getName());
    }

    @Test
    public void quandoCriarUsuarioComCpfOuCnpjExistente_entaoLancaExcecao() {
        when(userRepository.findBycpforcnpj(any())).thenReturn(Optional.of(user));
        assertThrows(RuntimeException.class, () -> userService.createUser(userRequestDTO));
    }

    @Test
    public void quandoBuscarTodosUsuarios_entaoRetornaPaginaDeUsuarios() {
        Page<User> usersPage = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(usersPage);
        when(userResponseMapperDTO.userToUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        Page<UserResponseDTO> result = userService.getAllUsers(PageRequest.of(0, 10));
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(user.getName());
    }

    @Test
    public void quandoBuscarUsuarioPorId_entaoRetornaUsuario() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userResponseMapperDTO.userToUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO foundUser = userService.getUserById(userId);
        assertThat(foundUser.getName()).isSameAs(user.getName());
    }

    @Test
    public void quandoAtualizarUsuario_entaoRetornaUsuarioAtualizado() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userResponseMapperDTO.userToUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);
        when(departmentService.getDepartment(any())).thenReturn(new Department());

        UserResponseDTO updatedUser = userService.updateUser(userId, userRequestDTO);
        assertThat(updatedUser.getName()).isSameAs(user.getName());
    }

    @Test
    public void quandoDeletarUsuario_entaoUsuarioEDeletado() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(userId);
        verify(userRepository, times(1)).delete(user);
    }
}
