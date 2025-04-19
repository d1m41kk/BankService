package Entities.Services;

import Enums.OperationType;
import Models.Operation;
import Repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс сервиса операций
 */
@Service
public class OperationService {
    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * Метод для получения истории операций конкретного пользователя
     */
    public List<Operation> getOperations(String id) {
        List<Operation> operations = operationRepository.findOperationsByAccountId(id);
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
    public void addOperation(String accountId, OperationType operationType, Double amount) {
        operationRepository.save(new Operation(accountId, operationType, amount));
    }
}
