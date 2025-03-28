package Entities.Models;

import Enums.HairColor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Класс, представляющий счет пользователя.
 */
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "UUID")
    public String id;

    @Column(name = "owner_login")
    public String ownerLogin;

    @Column(name = "balance")
    public double balance;
    public Account(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Account() {

    }
}
