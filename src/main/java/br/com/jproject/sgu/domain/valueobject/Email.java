package br.com.jproject.sgu.domain.valueobject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Email {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private String value;
    
    public Email(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        
        String trimmedEmail = email.trim().toLowerCase();
        
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Email deve ter um formato válido");
        }
        
        if (trimmedEmail.length() > 255) {
            throw new IllegalArgumentException("Email deve ter no máximo 255 caracteres");
        }
        
        this.value = trimmedEmail;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
