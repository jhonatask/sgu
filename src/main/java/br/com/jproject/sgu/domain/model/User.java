package br.com.jproject.sgu.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "O nome não pode estar em branco")
    @NotNull(message = "O nome não pode null")
    private String name;

    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email não pode estar em branco")
    private String email;

    @NotBlank(message = "O telefone não pode estar em branco")
    @NotNull(message = "O telefone não pode null")
    private String telefone;

    @NotBlank
    @NotNull
    @Column(unique = true)
    private String cpforcnpj;

    @Column(name = "password")
    private String password;

    @Column(name = "datacadastro")
    private Date datacadastro;

    @Column(name = "dataalteracao")
    private Date dataalteracao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", unique = true)
    @JsonBackReference
    private Department department;
}
