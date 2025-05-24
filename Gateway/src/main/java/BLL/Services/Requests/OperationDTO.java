package BLL.Services.Requests;

import BLL.Services.Enums.OperationType;

public record OperationDTO(String id, String accountId, OperationType operationType, Double amount) {
}
