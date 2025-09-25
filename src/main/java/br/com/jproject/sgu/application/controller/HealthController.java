package br.com.jproject.sgu.application.controller;

import br.com.jproject.sgu.domain.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Tag(name = "Health Check", description = "Endpoints para verificação de saúde da aplicação")
public class HealthController {

    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Verificação básica de saúde")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "SGU API");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/detailed")
    @Operation(summary = "Verificação detalhada de saúde")
    public ResponseEntity<Map<String, Object>> detailedHealth() {
        Map<String, Object> health = new HashMap<>();
        
        try {
            // Verificar conexão com banco
            long userCount = userRepository.count();
            
            health.put("status", "UP");
            health.put("timestamp", LocalDateTime.now());
            health.put("service", "SGU API");
            health.put("database", "UP");
            health.put("userCount", userCount);
            health.put("version", "1.0.0");
            
        } catch (Exception e) {
            health.put("status", "DOWN");
            health.put("timestamp", LocalDateTime.now());
            health.put("service", "SGU API");
            health.put("database", "DOWN");
            health.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(health);
    }
}
