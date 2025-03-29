package Repositories;

import Abstractions.IUserRepository;
import Entities.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepository implements IUserRepository {
    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> GetUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }

    public User GetUser(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, username);
        }
    }

    public void AddUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
