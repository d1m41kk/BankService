package Entities.Services;

import Enums.HairColor;
import Kafka.KafkaSender;
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
    private final KafkaSender kafkaSender;

    @Autowired
    public UserService(UserRepository userRepository, KafkaSender kafkaSender) {
        this.userRepository = userRepository;
        this.kafkaSender = kafkaSender;
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
        String json = String.format(
                "{\"event\":\"UserCreated\", \"login\":\"%s\", \"name\":\"%s\", \"age\":%d, \"sex\":%s, \"hairColor\":\"%s\"}",
                login, name, age, sex, hairColor);
        kafkaSender.sendClientEvent(login, json);
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
        String json = String.format(
                "{\"event\":\"FriendshipCreated\", \"user\":\"%s\", \"friend\":\"%s\"}",
                user_login, friend_login);
        kafkaSender.sendClientEvent(user_login, json);
    }
    @Transactional
    public void deleteUsersByLogin(String login) {
        userRepository.deleteUsersByLogin(login);
        String json = String.format("{\"event\":\"UserDeleted\", \"login\":\"%s\"}", login);
        kafkaSender.sendClientEvent(login, json);
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
        String json = String.format(
                "{\"event\":\"FriendshipDeleted\", \"user\":\"%s\", \"friend\":\"%s\"}",
                user_login, friend_login);
        kafkaSender.sendClientEvent(user_login, json);
    }

    public List<User> getFriends(String login) {
        User user = userRepository.getUserByLogin(login);
        return user.friends;
    }
}