package Entities.Services;

import Abstractions.IUserRepository;
import Entities.Models.User;

import java.util.List;

/**
 * Класс, представляющий сервис пользователя
 */

public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void AddUser(User user) {
        userRepository.AddUser(user);
    }

    public User GetUser(String login) {
        List<User> users = userRepository.GetUsers();
        for (User user : users) {
            if (user.login.equals(login)) {
                return user;
            }
        }
        return null;
    }
}
