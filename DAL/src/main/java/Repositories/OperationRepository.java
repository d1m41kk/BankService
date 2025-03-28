package Repositories;

import Abstractions.IOperationRepository;
import Entities.Models.Operation;
import Enums.OperationType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

public class OperationRepository implements IOperationRepository {
    private final EntityManager entityManager;

    public OperationRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<Operation> GetOperations() {
        return entityManager.createQuery("SELECT o FROM Operation o", Operation.class).getResultList();
    }

    @Transactional
    public void AddOperation(String accountId, OperationType operationType, Double amount) {
        Operation operation = new Operation();
        operation.accountId = accountId;
        operation.operationType = operationType;
        operation.amount = amount;

        entityManager.getTransaction().begin();
        entityManager.persist(operation);
        entityManager.getTransaction().commit();
    }
}
