package Repositories;

import DTO.AccountEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEventRepository extends JpaRepository<AccountEvent, String> {}