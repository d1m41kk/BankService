package Entities.Services;

import Enums.OperationType;
import Models.Account;
import Models.Operation;
import Repositories.AccountRepository;
import Repositories.OperationRepository;
import Requests.CreateAccountRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Класс, представляющий сервис аккаунта.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    /**
     * Метод для добавления аккаунта
     */
    public void createAccount(CreateAccountRequest request) {

        Account account = new Account(request.ownerId());

        accountRepository.save(account);
    }
    /**
     * Метод для получения данных аккаунта
     */
    public Account getAccount(String id) {
        if (accountRepository.findAccountById(id) == null) {
            throw new NoSuchElementException("Аккаунт не найден");
        }
        return accountRepository.findAccountById(id);
    }
    /**
     * Метод для удаления аккаунта
     */
    public void deleteAccount(Account account) {
        accountRepository.deleteAccountById(account.getId());
    }
    /**
     * Метод для снятия наличных
     */
    public void withdraw(String id, Double amount) {
        if (accountRepository.findAccountById(id).getBalance() >= amount) {
            Account account = accountRepository.findAccountById(id);
            account.setBalance(account.getBalance() - amount);
            OperationType operationType = OperationType.Withdrawal;
            Operation operation = new Operation(id, operationType, amount);
            operationRepository.save(operation);
            accountRepository.save(account);
        }
    }
    /**
     * Метод для внесения наличных
     */
    public void deposit(String id, Double amount) {
        Account account = accountRepository.findAccountById(id);
        account.setBalance(account.getBalance() + amount);
        OperationType operationType = OperationType.Deposit;
        Operation operation = new Operation(id, operationType, amount);
        operationRepository.save(operation);
        accountRepository.save(account);
    }
    /**
     * Метод для вывода баланса
     */
    public Double getBalance(String id) {
        if (accountRepository.findAccountById(id) != null) {
            return accountRepository.findAccountById(id).getBalance();
        }
        else {
            return null;
        }
    }
    @Transactional
    public void deleteAccountById(String id) {
        operationRepository.deleteAllByAccountId(id);
        accountRepository.deleteAccountById(id);
    }
}