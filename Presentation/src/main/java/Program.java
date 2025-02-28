import Abstractions.IAccountRepository;
import Abstractions.IAdminRepository;
import Abstractions.IOperationRepository;
import ConsoleInterface.ATMConsoleInterface;
import Entities.Services.AccountService;
import Entities.Services.AdminService;
import Entities.Services.OperationService;
import Repositories.AccountRepository;
import Repositories.AdminRepository;
import Repositories.OperationRepository;

public class Program {
    public static void main(String[] args) {
        IAccountRepository accountRepository = new AccountRepository(); // Реализация репозитория
        IAdminRepository adminRepository = new AdminRepository(); // Реализация репозитория
        IOperationRepository operationRepository = new OperationRepository(); // Реализация репозитория

        AccountService accountService = new AccountService(accountRepository);
        AdminService adminService = new AdminService(adminRepository);
        OperationService operationService = new OperationService(operationRepository);

        ATMConsoleInterface consoleInterface = new ATMConsoleInterface(accountService, adminService, operationService);
        consoleInterface.Run();
    }
}
