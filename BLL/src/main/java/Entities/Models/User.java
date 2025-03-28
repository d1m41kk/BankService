package Entities.Models;

import Enums.HairColor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представояющий пользователя
 */

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "login", nullable = false, unique = true)
    public String login;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "sex")
    public Boolean sex;

    @Column(name = "age")
    public Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    public HairColor hairColor;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_login"),
            inverseJoinColumns = @JoinColumn(name = "friend_login")
    )
    public List<User> friends = new ArrayList<>();

    public User(String login, String name, Boolean sex, Integer age, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.hairColor = hairColor;
    }

    public User() {
    }
}