package Abstractions;

import Entities.Models.Admin;

public interface IAdminRepository {
    Admin GetAdmin(Integer id, String password);
}
