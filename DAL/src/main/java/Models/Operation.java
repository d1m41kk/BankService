package Models;

import Enums.OperationType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * Класс, представляющий операции по счету.
 */
@Entity
@Table (name = "operations")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Operation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "UUID")
    private String id;

    public String getId(){
        return id;
    }
    public void setId(String operationId){
        this.id = operationId;
    }

    @Column(name = "account_id")
    private String accountId;

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Column(name = "operation_type")
    private OperationType operationType;

    public OperationType getOperationType() {
        return operationType;
    }
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Column(name = "amount")
    private double amount;

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Operation(String accountId, OperationType operationType, Double amount) {
        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public Operation() {}
}