package App;

import Controllers.AccountsController;
import Controllers.OperationController;
import Controllers.UserController;
import Models.Account;
import Models.Operation;
import Models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"Entities.Services"})
@ComponentScan(basePackageClasses = {AccountsController.class, OperationController.class, UserController.class})
@SpringBootApplication
@EnableJpaRepositories(basePackages = "Repositories")
@EntityScan(basePackageClasses = {Account.class, Operation.class, User.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}