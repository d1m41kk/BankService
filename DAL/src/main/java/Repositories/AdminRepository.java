package Repositories;

import Abstractions.IAccountRepository;
import Abstractions.IAdminRepository;
import Entities.Models.Admin;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс, представляющий репозиторий админов.
 */
public class AdminRepository implements IAdminRepository {
    private final List<Admin> Admins = new ArrayList<Admin>();

    public AdminRepository() {
        Entities.Models.Admin admin = new Admin(228, "admin");
        Admins.add(admin);
    }

    public Admin GetAdmin(Integer id, String password) {
        for (Admin admin : Admins) {
            if (admin.Id.equals(id) && admin.Password.equals(password)) {
                return admin;
            }
        }
        return null;
    }
}
