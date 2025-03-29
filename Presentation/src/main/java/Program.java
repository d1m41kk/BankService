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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        IAccountRepository accountRepository = new AccountRepository(sessionFactory);
        IOperationRepository operationRepository = new OperationRepository(sessionFactory);
        ATMConsoleInterface consoleInterface = getAtmConsoleInterface(sessionFactory, accountRepository, operationRepository);
        consoleInterface.Run();

        HibernateUtil.shutdown();
    }

    private static ATMConsoleInterface getAtmConsoleInterface(SessionFactory sessionFactory, IAccountRepository accountRepository, IOperationRepository operationRepository) {
        IUserRepository userRepository = new UserRepository(sessionFactory);

        AccountService accountService = new AccountService(accountRepository);
        OperationService operationService = new OperationService(operationRepository);
        UserService userService = new UserService(userRepository);

        ATMConsoleInterface consoleInterface = new ATMConsoleInterface(
                accountService,
                operationService,
                userService
        );
        return consoleInterface;
    }
}
