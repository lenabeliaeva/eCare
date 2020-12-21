package com.example.demo.dao;

import com.example.demo.models.Tariff;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TariffDaoImpl implements TariffDao {
    private static final EntityManagerFactory emfObj;
    static {
        emfObj = Persistence.createEntityManagerFactory("eCare");
    }
    private final EntityManager entityManager = emfObj.createEntityManager();

    @Override
    public void add(Tariff tariff) {
        entityManager.getTransaction().begin();
        entityManager.persist(tariff);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Tariff> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Tariff e");
        return query.getResultList();
    }
}
