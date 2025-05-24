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
        return operationRepository.findOperationsByAccountId(id);
    }
}
