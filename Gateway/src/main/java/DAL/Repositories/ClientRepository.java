package DAL.Repositories;

import DAL.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Client getClientByLogin(String login);
    void deleteClientByLogin(String login);
}
