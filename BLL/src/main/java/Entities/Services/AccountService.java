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
    public Account GetAccount(Account account) {
        return accountRepository.GetAccount(account.Id);
    }
    /**
     * Метод для удаления аккаунта
     */
    public void DeleteAccount(Account account) {
        accountRepository.DeleteAccount(account.Id);
    }
    /**
     * Метод для снятия наличных
     */
    public void Withdraw(Integer id, Double amount) {
        if (accountRepository.GetAccount(id).Balance >= amount) {
            accountRepository.GetAccount(id).Balance -= amount;
        }
    }
    /**
     * Метод для внесения наличных
     */
    public void Deposit(Integer id, Double amount) {
        accountRepository.GetAccount(id).Balance += amount;
    }
    /**
     * Метод для вывода баланса
     */
    public Double GetBalance(Integer id) {
        if (accountRepository.GetAccount(id) != null) {
            return accountRepository.GetAccount(id).Balance;
        }
        else {
            return null;
        }
    }
}