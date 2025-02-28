package Repositories;

import Abstractions.IAccountRepository;
import Entities.Models.Account;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс, представляющий репозиторий пользователей.
 */
public class AccountRepository implements IAccountRepository {
    private List<Account> Accounts = new ArrayList<>();

    public AccountRepository() {
        Accounts = new ArrayList<>();
    }
    /**
     * Метод для получения информации аккаунта
     */
    public Account GetAccount(int id) {
        for (Account account : Accounts) {
            if (account.Id.equals(id)) {
                return account;
            }
        }
        return null;
    }
    /**
     * Метод для добавления аккаунта в репозиторий аккаунтов
     */
    public void AddAccount(Account account) {
        Accounts.add(account);
    }
    /**
     * Метод для удаления аккаунта
     */
    public void DeleteAccount(int id) {
        Accounts.removeIf(account -> account.Id.equals(id));
    }
}
