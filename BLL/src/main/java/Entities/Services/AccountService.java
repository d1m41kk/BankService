package Entities.Services;

import Abstractions.IAccountRepository;
import Entities.Models.Account;

/**
 * Класс, представляющий сервис аккаунта.
 */

public class AccountService {
    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    /**
     * Метод для добавления аккаунта
     */
    public void AddAccount(Account account) {
        accountRepository.AddAccount(account);
    }
    /**
     * Метод для получения данных аккаунта
     */
    public Account GetAccount(String id) {
        return accountRepository.GetAccount(id);
    }
    /**
     * Метод для удаления аккаунта
     */
    public void DeleteAccount(Account account) {
        accountRepository.DeleteAccount(account.id);
    }
    /**
     * Метод для снятия наличных
     */
    public void Withdraw(String id, Double amount) {
        if (accountRepository.GetAccount(id).balance >= amount) {
            accountRepository.UpdateBalance(id, -amount);
        }
    }
    /**
     * Метод для внесения наличных
     */
    public void Deposit(String id, Double amount) {
        accountRepository.UpdateBalance(id, amount);
    }
    /**
     * Метод для вывода баланса
     */
    public Double GetBalance(String id) {
        if (accountRepository.GetAccount(String.valueOf(id)) != null) {
            return accountRepository.GetAccount(id).balance;
        }
        else {
            return null;
        }
    }
}