package br.com.jproject.sgu.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDTO {

    private UUID id;
    private String name;
    private List<UserResponseDTO> users;
}
