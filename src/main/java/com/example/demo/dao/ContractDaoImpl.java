package com.example.demo.dao;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

@Repository
public class ContractDaoImpl implements ContractDao {

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("eCare");
    }

    private final EntityManager em = emf.createEntityManager();

    @Override
    public Contract getById(long id) {
        return em.find(Contract.class, id);
    }

    @Override
    public List<Contract> getByClientId(long clientId) {
        return em
                .createQuery("select c from Contract c where c.client.id = :cId")
                .setParameter("cId", clientId)
                .getResultList();
    }

    @Override
    public void save(Contract contract) {
        em.getTransaction().begin();
        em.persist(contract);
        em.getTransaction().commit();
    }

    @Override
    public void update(Contract contract) {
        em.getTransaction().begin();
        em.merge(contract);
        em.getTransaction().commit();

    }

    @Override
    public void delete(Contract contract) {
        em.getTransaction().begin();
        em.remove(contract);
        em.getTransaction().commit();
    }
}
