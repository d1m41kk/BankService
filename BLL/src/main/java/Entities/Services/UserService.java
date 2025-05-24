package Entities.Services;

import Enums.HairColor;
import Models.User;
import Repositories.UserRepository;
import jakarta.transaction.Transactional;
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
        user.setLogin(login);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setHairColor(hairColor);
        userRepository.save(user);
    }

    public User getUserByLogin(String login) {
        if (userRepository.getUserByLogin(login) == null) {
            throw new NoSuchElementException("Пользователь не найден");
        }
        return userRepository.getUserByLogin(login);
    }

    public List<User> getUsersByHairColorAndSex(HairColor hairColor, Boolean sex){
        List<User> users = userRepository.getUsersByHairColorAndSex(hairColor, sex);
        if (users.isEmpty()) {
            throw new NoSuchElementException("Пользователя с такими данными нет");
        }
        return users;
    }
    public void createFriendship(String user_login, String friend_login) {
        User user = userRepository.getUserByLogin(user_login);
        User friend = userRepository.getUserByLogin(friend_login);
        if (user == null || friend == null) {
            throw new NoSuchElementException("Пользователя с таким логином нет");
        }

        if (!user.friends.contains(friend)) {
            user.friends.add(friend);
        }
        if (!friend.friends.contains(user)) {
            friend.friends.add(user);
        }
        userRepository.save(user);
        userRepository.save(friend);
    }
    @Transactional
    public void deleteUsersByLogin(String login) {
        userRepository.deleteUsersByLogin(login);
    }
    @Transactional
    public void deleteFriendship(String user_login, String friend_login) {
        User user = userRepository.getUserByLogin(user_login);
        User friend = userRepository.getUserByLogin(friend_login);
        if (user == null || friend == null) {
            throw new NoSuchElementException("Пользователя с таким логином нет");
        }
        user.friends.remove(friend);
        friend.friends.remove(user);
        userRepository.save(user);
        userRepository.save(friend);
    }
}