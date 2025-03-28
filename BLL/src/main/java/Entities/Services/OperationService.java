package Entities.Services;

import Abstractions.IOperationRepository;
import Entities.Models.Operation;
import Enums.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс сервиса операций
 */

public class OperationService {
    private final IOperationRepository operationRepository;

    public OperationService(IOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * Метод для получения истории операций конкретного пользователя
     */
    public List<Operation> GetOperations(String id) {
        List<Operation> operations = operationRepository.GetOperations();
        List<Operation> filteredOperations = new ArrayList<>();
        for (Operation operation : operations) {
            if (Objects.equals(operation.accountId, id)) {
                filteredOperations.add(operation);
            }
        }
        return filteredOperations;
    }
    /**
     * Метод для добавления операции в историю
     */
    public void AddOperation(String accountId, OperationType operationType, Double amount) {
        operationRepository.AddOperation(accountId, operationType, amount);
    }
}
