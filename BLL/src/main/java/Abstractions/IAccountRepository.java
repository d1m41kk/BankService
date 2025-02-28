package Abstractions;

import Entities.Models.Account;

public interface IAccountRepository {
    Account GetAccount(int id);
    void AddAccount(Account account);
    void DeleteAccount(int id);
}