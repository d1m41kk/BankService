package Abstractions;

import Entities.Models.Operation;
import Enums.OperationType;

import java.util.List;

/**
 * Интерфейс репозитория операций
 */


public interface IOperationRepository {
    List<Operation> GetOperations();
    void AddOperation(String accountId, OperationType operationType, Double amount);
}