package Models;

import Enums.HairColor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * Класс, представляющий счет пользователя.
 */
@Entity
@Table(name="accounts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
