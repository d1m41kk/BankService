package Entities.Models;

import Enums.HairColor;
/**
 * Класс, представляющий аккаунт пользователя.
 */
public class Account {
    public Integer Id;
    public Double Balance = 0.0;
    public Integer Pin;
    public String Name;
    public Integer Age;
    public HairColor HairColor;

    public Account(Integer id, Integer pin, String name, Integer age, HairColor hairColor) {
        Id = id;
        Pin = pin;
        Name = name;
        Age = age;
        HairColor = hairColor;
    }
}
