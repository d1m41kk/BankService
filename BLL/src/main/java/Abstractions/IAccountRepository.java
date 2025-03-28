package Abstractions;

import Entities.Models.Account;

/**
 * Интерфейс репозитория счетов
 */

public interface IAccountRepository {
    Account GetAccount(String id);
    void AddAccount(Account account);
    void DeleteAccount(String id);
    void UpdateBalance(String id, Double amount);
}