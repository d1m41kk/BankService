package DAL.Repositories;

import DAL.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin getAdminByLogin(String login);
    void deleteAdminByLogin(String login);
}
