package Entities.Models;

import Enums.OperationType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Класс, представляющий операции по счету.
 */
@Entity
@Table (name = "operations")
public class Operation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "UUID")
    public String id;

    @Column(name = "account_id")
    public String accountId;

    @Column(name = "operation_type")
    public OperationType operationType;

    @Column(name = "amount")
    public double amount;

    public Operation(String accountId, OperationType operationType, Double amount) {
        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public Operation() {}
}