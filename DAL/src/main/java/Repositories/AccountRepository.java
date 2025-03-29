package Repositories;

import Abstractions.IAccountRepository;
import Entities.Models.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AccountRepository implements IAccountRepository {
    private final SessionFactory sessionFactory;

    public AccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Account GetAccount(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Account.class, id);
        }
    }

    public void AddAccount(Account account) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void DeleteAccount(String id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            Account account = session.get(Account.class, id);
            if (account != null) {
                transaction = session.beginTransaction();
                session.delete(account);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void UpdateBalance(String id, Double amount) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Account account = session.get(Account.class, id);
            if (account != null) {
                account.balance += amount;
                session.update(account);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
