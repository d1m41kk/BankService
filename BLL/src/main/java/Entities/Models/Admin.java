package Entities.Models;
/**
 * Класс, представляющий админа.
 */
public class Admin {
    public Integer Id;
    public String Password;

    public Admin(Integer id, String password) {
        Id = id;
        Password = password;
    }
}
