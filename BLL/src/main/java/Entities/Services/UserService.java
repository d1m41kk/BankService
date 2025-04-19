package Entities.Services;

import Enums.HairColor;
import Models.User;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс, представляющий сервис пользователя
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public void addUser(String login, String name, Boolean sex, Integer age, HairColor hairColor) {
        User user = new User();
        user.login = login;
        user.name = name;
        user.sex = sex;
        user.age = age;
        user.hairColor = hairColor;
        userRepository.save(user);
    }

    public User getUserByLogin(String login) {
        if (userRepository.getUserByLogin(login) == null) {
            throw new NoSuchElementException("Пользователь не найден");
        }
        return userRepository.getUserByLogin(login);
    }
}
