import Abstractions.IAccountRepository;
import Abstractions.IOperationRepository;
import Abstractions.IUserRepository;
import ConsoleInterface.ATMConsoleInterface;
import Entities.Services.AccountService;
import Entities.Services.OperationService;
import Entities.Services.UserService;
import Repositories.AccountRepository;
import Repositories.HibernateUtil;
import Repositories.OperationRepository;
import Repositories.UserRepository;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Program {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = HibernateUtil.getEntityManagerFactory();

        // Создаем репозитории, передавая EntityManagerFactory
        IAccountRepository accountRepository = new AccountRepository(entityManagerFactory);
        IOperationRepository operationRepository = new OperationRepository(entityManagerFactory);
        ATMConsoleInterface consoleInterface = getAtmConsoleInterface(entityManagerFactory, accountRepository, operationRepository);
        consoleInterface.Run();

        // Закрываем соединение
        HibernateUtil.shutdown();
    }

    private static ATMConsoleInterface getAtmConsoleInterface(EntityManagerFactory entityManagerFactory, IAccountRepository accountRepository, IOperationRepository operationRepository) {
        IUserRepository userRepository = new UserRepository(entityManagerFactory);

        // Создаем сервисы
        AccountService accountService = new AccountService(accountRepository);
        OperationService operationService = new OperationService(operationRepository);
        UserService userService = new UserService(userRepository);

        // Запускаем консольный интерфейс
        ATMConsoleInterface consoleInterface = new ATMConsoleInterface(
                accountService,
                operationService,
                userService
        );
        return consoleInterface;
    }
}
