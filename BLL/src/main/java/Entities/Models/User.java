package Entities.Models;

import Enums.HairColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представояющий пользователя
 */

public class User {
    public String Login;
    public String Name;
    public Boolean Sex;
    public Integer Age;
    public HairColor HairColor;
    public List<User> Friends;

    public User(String login, String name, Boolean sex, Integer age, HairColor hairColor) {
        Login = login;
        Name = name;
        Sex = sex;
        Age = age;
        HairColor = hairColor;
        Friends = new ArrayList<>();
    }
}
