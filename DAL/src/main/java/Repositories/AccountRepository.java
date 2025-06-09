package Repositories;

import Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findAccountById(String id);

    void deleteAccountById(String id);

    List<Account> findAccountByOwnerLogin(String ownerLogin);
}
