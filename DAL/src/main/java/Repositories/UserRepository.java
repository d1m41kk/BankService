package Repositories;

import Enums.HairColor;
import Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLogin(String login);
    List<User> getUsersByHairColorAndSex(HairColor hairColor, Boolean sex);
}
