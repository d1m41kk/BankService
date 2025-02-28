package Entities.Models;

import Enums.OperationType;
/**
 * Класс, представляющий операции по счету.
 */
public class Operation {
    public int AccountId;
    public OperationType OperationType;
    public Double Amount;

    public Operation(Integer accountId, OperationType operationType, Double amount) {
        AccountId = accountId;
        OperationType = operationType;
        Amount = amount;
    }
}
