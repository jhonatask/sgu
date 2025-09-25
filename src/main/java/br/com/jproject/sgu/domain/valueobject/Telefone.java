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
public class Telefone {
    
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(
        "^\\(?[1-9]{2}\\)?[0-9]{4,5}-?[0-9]{4}$"
    );
    
    private String value;
    
    public Telefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio");
        }
        
        String cleanedTelefone = telefone.replaceAll("[^0-9]", "");
        
        if (!TELEFONE_PATTERN.matcher(telefone).matches()) {
            throw new IllegalArgumentException("Telefone deve ter formato válido: (XX)XXXXX-XXXX ou (XX)XXXX-XXXX");
        }
        
        if (cleanedTelefone.length() < 10 || cleanedTelefone.length() > 11) {
            throw new IllegalArgumentException("Telefone deve ter 10 ou 11 dígitos");
        }
        
        this.value = telefone;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
