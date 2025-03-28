package Repositories;

import Abstractions.IAccountRepository;
import Entities.Models.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

public class AccountRepository implements IAccountRepository {
    private final EntityManager entityManager;

    public AccountRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    }

    public Account GetAccount(String id) {
        return entityManager.find(Account.class, id);
    }

    @Transactional
    public void AddAccount(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
    }

    @Transactional
    public void DeleteAccount(String id) {
        Account account = GetAccount(id);
        if (account != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(account);
            entityManager.getTransaction().commit();
        }
    }

    @Transactional
    public void UpdateBalance(String id, Double amount) {
        entityManager.getTransaction().begin();

        Account account = entityManager.find(Account.class, id);
        if (account != null) {
            account.balance += amount;
            entityManager.merge(account);
        }

        entityManager.getTransaction().commit();
    }
}
