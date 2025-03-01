package Entities.Models;

import Enums.HairColor;
/**
 * Класс, представляющий счет пользователя.
 */
public class Account {
    public Integer Id;
    public String OwnerLogin;
    public Double Balance;
    public Account(Integer id, String ownerLogin) {
        Id = id;
        OwnerLogin = ownerLogin;
        Balance = 0.0;
    }
}
