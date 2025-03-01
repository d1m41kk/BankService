package Abstractions;

import Entities.Models.Account;

/**
 * Интерфейс репозитория счетов
 */

public interface IAccountRepository {
    Account GetAccount(int id);
    void AddAccount(Account account);
    void DeleteAccount(int id);
}