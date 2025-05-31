package DTO;

import jakarta.persistence.*;

@Entity
@Table(name = "user_event")
public class UserEvent {
    @Id
    @Column(unique = true, nullable = false)
    private String login;

    @Lob
    private String description;

    public UserEvent(String key, String message) {
        this.login = key;
        this.description = message;
    }

    public UserEvent() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
