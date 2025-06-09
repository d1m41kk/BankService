package Entities.Services;

import Enums.OperationType;
import Kafka.KafkaSender;
import Models.Account;
import Models.Operation;
import Repositories.AccountRepository;
import Repositories.OperationRepository;
import Requests.CreateAccountRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс, представляющий сервис аккаунта.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final KafkaSender kafkaSender;
    @Autowired
    public AccountService(AccountRepository accountRepository, OperationRepository operationRepository, KafkaSender kafkaSender) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.kafkaSender = kafkaSender;
    }
    /**
     * Метод для добавления аккаунта
     */
    public void createAccount(CreateAccountRequest request) {

        Account account = new Account(request.ownerId());
        accountRepository.save(account);
        String json = String.format("{\"event\":\"AccountCreated\", \"accountId\":\"%s\"}", account.getId());
        kafkaSender.sendAccountEvent(account.getId(), json);
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

    public List<Account> getAccounts(String ownerLogin) {
        return accountRepository.findAccountByOwnerLogin(ownerLogin);
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
            String json = String.format("{\"event\":\"Withdraw\", \"accountId\":\"%s\", \"amount\":%s}", id, amount);
            kafkaSender.sendAccountEvent(id, json);
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
        String json = String.format("{\"event\":\"Deposit\", \"accountId\":\"%s\", \"amount\":%s}", id, amount);
        kafkaSender.sendAccountEvent(id, json);
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
        String json = String.format("{\"event\":\"AccountDeleted\", \"accountId\":\"%s\"}", id);
        kafkaSender.sendAccountEvent(id, json);
    }
}