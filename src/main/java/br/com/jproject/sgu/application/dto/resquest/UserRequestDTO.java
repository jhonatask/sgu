package br.com.jproject.sgu.application.dto.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    public String name;
    public String email;
    public String password;
    public String telefone;
    public String cpforcnpj;
    public UUID department;
}
