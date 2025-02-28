package Abstractions;

import Entities.Models.Operation;
import Enums.OperationType;

import java.util.List;

public interface IOperationRepository {
    List<Operation> GetOperations();
    void AddOperation(Integer accountId, OperationType operationType, Double amount);
}