package Repositories;

import Abstractions.IOperationRepository;
import Entities.Models.Operation;
import Enums.OperationType;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс, представляющий репозиторий операций.
 */
public class OperationRepository implements IOperationRepository {
    private final List<Operation> operations = new ArrayList<Operation>();

    public List<Operation> GetOperations() {
        return operations;
    }

    /**
     *Метод для добавления операции в репозиторий
     */
    public void AddOperation(Integer accountId, OperationType operationType, Double amount) {
        operations.add(new Operation(accountId, operationType, amount));
    }
}
