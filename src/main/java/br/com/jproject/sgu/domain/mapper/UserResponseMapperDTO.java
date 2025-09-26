package br.com.jproject.sgu.domain.mapper;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.valueobject.Email;
import br.com.jproject.sgu.domain.valueobject.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserResponseMapperDTO {
    
    @Mapping(source = "email", target = "email", qualifiedByName = "emailToString")
    @Mapping(source = "telefone", target = "telefone", qualifiedByName = "telefoneToString")
    UserResponseDTO userToUserResponseDTO(User entity);
    
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(source = "telefone", target = "telefone", qualifiedByName = "stringToTelefone")
    User userResponseDTOToUser(UserResponseDTO entity);
    
    @org.mapstruct.Named("emailToString")
    default String emailToString(Email email) {
        return email != null ? email.toString() : null;
    }
    
    @org.mapstruct.Named("telefoneToString")
    default String telefoneToString(Telefone telefone) {
        return telefone != null ? telefone.toString() : null;
    }
    
    @org.mapstruct.Named("stringToEmail")
    default Email stringToEmail(String email) {
        return email != null ? new Email(email) : null;
    }
    
    @org.mapstruct.Named("stringToTelefone")
    default Telefone stringToTelefone(String telefone) {
        return telefone != null ? new Telefone(telefone) : null;
    }
}
