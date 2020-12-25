package com.example.demo.dao;

import com.example.demo.models.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
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
        Query query = entityManager.createQuery("select e from Tariff e");
        return query.getResultList();
    }

    @Override
    public void delete(Tariff tariff) {
        entityManager.getTransaction().begin();
        entityManager.remove(tariff);
        entityManager.getTransaction().commit();
    }

    @Override
    public Tariff getById(long id) {
        entityManager.getTransaction().begin();
        Tariff tariff = entityManager.find(Tariff.class, id);
        entityManager.getTransaction().commit();
        return tariff;
    }
}
