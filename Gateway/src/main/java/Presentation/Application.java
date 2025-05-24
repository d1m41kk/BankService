package Presentation;
import Controllers.AuthController;
import DAL.Models.Admin;
import DAL.Models.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "BLL.JWT",
        "BLL.Services",
        "BLL.Config",
        "Security.Config",
}, basePackageClasses = {AuthController.class})
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"DAL.Repositories"})
@EntityScan(basePackageClasses = {Admin.class, Client.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}