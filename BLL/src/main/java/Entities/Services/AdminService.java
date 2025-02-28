package Entities.Services;

import Abstractions.IAdminRepository;

/**
 * Класс сервиса админа
 */
public class AdminService {
    private final IAdminRepository adminRepository;

    public AdminService(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Метода для входа в аккаунт админа
     */
    public Boolean Login(Integer id, String password) {
        return adminRepository.GetAdmin(id, password) != null;
    }
}
