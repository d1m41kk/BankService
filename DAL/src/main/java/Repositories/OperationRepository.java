package Repositories;

import Abstractions.IOperationRepository;
import Entities.Models.Operation;
import Enums.OperationType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class OperationRepository implements IOperationRepository {
    private final SessionFactory sessionFactory;

    public OperationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Operation> GetOperations() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT o FROM Operation o", Operation.class).getResultList();
        }
    }

    public void AddOperation(String accountId, OperationType operationType, Double amount) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Operation operation = new Operation();
            operation.accountId = accountId;
            operation.operationType = operationType;
            operation.amount = amount;

            session.save(operation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
