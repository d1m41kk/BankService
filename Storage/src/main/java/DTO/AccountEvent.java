package DTO;

import jakarta.persistence.*;

@Entity
@Table(name = "account_event")
public class AccountEvent {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Lob
    private String description;

    public AccountEvent(String key, String message) {
        this.id = key;
        this.description = message;
    }

    public AccountEvent() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
