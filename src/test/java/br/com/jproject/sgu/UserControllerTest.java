package br.com.jproject.sgu;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.domain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");


        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(UUID.randomUUID());
        userResponse.setName("John Doe");
        userResponse.setEmail("john.doe@example.com");


        Mockito.when(userService.createUser(Mockito.any(UserRequestDTO.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userResponse.getId().toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Page<UserResponseDTO> users = new PageImpl<>(Collections.singletonList(new UserResponseDTO()));

        Mockito.when(userService.getAllUsers(Mockito.any(Pageable.class))).thenReturn(users);

        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void testGetUserById() throws Exception {
        UUID userId = UUID.randomUUID();
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(userId);
        userResponse.setName("John Doe");
        userResponse.setEmail("john.doe@example.com");

        Mockito.when(userService.getUserById(userId)).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(userId);
        userResponse.setName("Jane Doe");
        userResponse.setEmail("jane.doe@example.com");

        Mockito.when(userService.updateUser(Mockito.eq(userId), Mockito.any(UserRequestDTO.class))).thenReturn(userResponse);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        UUID userId = UUID.randomUUID();

        Mockito.doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
