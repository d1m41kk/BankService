import Abstractions.IAccountRepository;
import Abstractions.IOperationRepository;
import Abstractions.IUserRepository;
import ConsoleInterface.ATMConsoleInterface;
import Entities.Models.User;
import Entities.Services.AccountService;
import Entities.Services.OperationService;
import Entities.Services.UserService;
import Repositories.AccountRepository;
import Repositories.OperationRepository;
import Repositories.UserRepository;

public class Program {
    public static void main(String[] args) {
        IAccountRepository accountRepository = new AccountRepository(); // Реализация репозитория
        IOperationRepository operationRepository = new OperationRepository(); // Реализация репозитория

        AccountService accountService = new AccountService(accountRepository);
        OperationService operationService = new OperationService(operationRepository);

        IUserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        ATMConsoleInterface consoleInterface = new ATMConsoleInterface(accountService, operationService, userService);
        consoleInterface.Run();
    }
}
