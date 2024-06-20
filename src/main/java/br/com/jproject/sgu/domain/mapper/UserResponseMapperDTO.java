package br.com.jproject.sgu.domain.mapper;


import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.domain.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserResponseMapperDTO {
    UserResponseDTO userToUserResponseDTO(User entity);
    User userResponseDTOToUser(UserResponseDTO entity);
}
