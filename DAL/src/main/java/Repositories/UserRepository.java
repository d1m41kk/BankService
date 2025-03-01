package Repositories;

import Abstractions.IUserRepository;
import Entities.Models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий репозиторий пользователей
 */


public class UserRepository implements IUserRepository {
    private final List<User> users = new ArrayList<>();

    /**
     * Метод выводит список всех пользователей
     */

    public List<User> GetUsers() {
        return users;
    }

    /**
     * Метод выводит пользователя по юзернейму
     */

    public User GetUser(String username) {
        for (User user : users) {
            if (user.Login.equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Метод добавляет пользователя в список пользователей
     */

    public void AddUser(User user) {
        users.add(user);
    }
}
