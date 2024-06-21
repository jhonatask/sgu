package br.com.jproject.sgu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SguApplication {

    public static void main(String[] args) {
        SpringApplication.run(SguApplication.class, args);
    }

}
