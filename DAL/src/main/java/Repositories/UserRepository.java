package Repositories;

import Abstractions.IUserRepository;
import Entities.Models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final EntityManager entityManager;

    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    }

    public List<User> GetUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User GetUser(String username) {
        return entityManager.find(User.class, username);
    }

    @Transactional
    public void AddUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }
}
