package br.com.jproject.sgu.application.dto.resquest;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.jproject.sgu.core.constants.ErrorMessages;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    
    @NotBlank(message = ErrorMessages.NAME_REQUIRED)
    @Size(min = 2, max = 100, message = ErrorMessages.NAME_SIZE)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = ErrorMessages.NAME_PATTERN)
    private String name;
    
    @NotBlank(message = ErrorMessages.EMAIL_REQUIRED)
    @Email(message = ErrorMessages.EMAIL_INVALID)
    @Size(max = 255, message = ErrorMessages.EMAIL_SIZE)
    private String email;
    
    @Size(min = 8, max = 128, message = ErrorMessages.PASSWORD_SIZE)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
             message = ErrorMessages.PASSWORD_PATTERN)
    private String password;
    
    @NotBlank(message = ErrorMessages.PHONE_REQUIRED)
    @Pattern(regexp = "^\\(?[1-9]{2}\\)?[0-9]{4,5}-?[0-9]{4}$", 
             message = ErrorMessages.PHONE_PATTERN)
    private String telefone;
    
    @NotBlank(message = ErrorMessages.CPF_REQUIRED)
    @Pattern(regexp = "^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}|\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2})$", 
             message = ErrorMessages.CPF_PATTERN)
    private String cpforcnpj;
    
    @NotNull(message = ErrorMessages.DEPARTMENT_REQUIRED)
    private UUID department;
}
