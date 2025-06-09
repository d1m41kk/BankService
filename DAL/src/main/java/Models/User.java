package Models;

import Enums.HairColor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представояющий пользователя
 */

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "login")
public class User {
    @Id
    @Column(name = "login", nullable = false)
    private String login;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "name", nullable = false)
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "sex")
    private Boolean sex;
    public Boolean getSex(){
        return sex;
    }
    public void setSex(Boolean sex){
        this.sex = sex;
    }

    @Column(name = "age")
    private Integer age;
    public Integer getAge(){
        return age;
    }
    public void setAge(Integer age){
        this.age = age;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    private HairColor hairColor;

    public HairColor getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_login"),
            inverseJoinColumns = @JoinColumn(name = "friend_login")
    )

    @JsonIgnore
    public List<User> friends = new ArrayList<>();

    public User() {
    }
}