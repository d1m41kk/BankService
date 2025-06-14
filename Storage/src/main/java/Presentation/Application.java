package Presentation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"Service", "Config"})
@EnableJpaRepositories(basePackages = "Repositories")
@EntityScan(basePackages = "DTO")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}