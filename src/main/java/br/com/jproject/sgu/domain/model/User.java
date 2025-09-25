package br.com.jproject.sgu.domain.model;

import br.com.jproject.sgu.domain.valueobject.Email;
import br.com.jproject.sgu.domain.valueobject.Telefone;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    @Column(length = 100)
    private String name;

    @Embedded
    private Email email;

    @Embedded
    private Telefone telefone;

    @NotBlank
    @NotNull
    @Column(unique = true, length = 20)
    private String cpforcnpj;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "datacadastro")
    private LocalDateTime datacadastro;

    @Column(name = "dataalteracao")
    private LocalDateTime dataalteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;
    
    // Métodos de negócio
    public void updateData(String name, Email email, Telefone telefone, String cpforcnpj) {
        this.name = name;
        this.email = email;
        this.telefone = telefone;
        this.cpforcnpj = cpforcnpj;
        this.dataalteracao = LocalDateTime.now();
    }
    
    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.dataalteracao = LocalDateTime.now();
    }
    
    public void setInitialData() {
        this.datacadastro = LocalDateTime.now();
        this.dataalteracao = LocalDateTime.now();
    }
}
