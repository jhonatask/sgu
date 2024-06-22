package br.com.jproject.sgu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.com.jproject.sgu.domain.model")
@EnableJpaRepositories(basePackages = "br.com.jproject.sgu.domain.repositories")
public class SguApplication {

    public static void main(String[] args) {
        SpringApplication.run(SguApplication.class, args);
    }

}
