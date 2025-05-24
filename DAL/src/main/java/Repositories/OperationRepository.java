package Repositories;

import Models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, String> {
    List<Operation> findOperationsByAccountId(String id);
    void deleteAllByAccountId(String id);
}