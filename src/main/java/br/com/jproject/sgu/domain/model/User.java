package br.com.jproject.sgu.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome não pode estar em branco")
    @NotNull(message = "O nome não pode null")
    private String name;

    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email não pode estar em branco")
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id", unique = true)
    private Department department;
}
