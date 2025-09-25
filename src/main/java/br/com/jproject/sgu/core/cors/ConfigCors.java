package br.com.jproject.sgu.core.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ConfigCors implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${app.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${app.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${app.cors.allow-credentials}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        List<String> methods = Arrays.asList(allowedMethods.split(","));
        
        registry.addMapping("/**")
                .allowedOrigins(origins.toArray(new String[0]))
                .allowedMethods(methods.toArray(new String[0]))
                .allowedHeaders("*")
                .allowCredentials(allowCredentials)
                .maxAge(3600L); // Cache preflight por 1 hora
    }
}