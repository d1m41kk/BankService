package Abstractions;

import Entities.Models.User;

import java.util.List;

/**
 * Интерфейс репозитория пользователей
 */

public interface IUserRepository {
    List<User> GetUsers();
    User GetUser(String username);
    void AddUser(User user);
}
