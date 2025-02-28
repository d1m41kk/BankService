package Entities.Services;

import Abstractions.IOperationRepository;
import Entities.Models.Operation;
import Enums.OperationType;

import java.util.ArrayList;
import java.util.List;

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
    public List<Operation> GetOperations(Integer id) {
        List<Operation> operations = operationRepository.GetOperations();
        List<Operation> filteredOperations = new ArrayList<>();
        for (Operation operation : operations) {
            if (operation.AccountId == id) {
                filteredOperations.add(operation);
            }
        }
        return filteredOperations;
    }
    /**
     * Метод для добавления операции в историю
     */
    public void AddOperation(Integer accountId, OperationType operationType, Double amount) {
        operationRepository.AddOperation(accountId, operationType, amount);
    }
}
