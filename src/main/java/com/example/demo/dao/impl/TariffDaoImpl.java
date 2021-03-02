package com.example.demo.dao.impl;

import com.example.demo.dao.api.TariffDao;
import com.example.demo.models.entities.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TariffDaoImpl implements TariffDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tariff add(Tariff tariff) {
        entityManager.persist(tariff);
        return tariff;
    }

    @Override
    public List<Tariff> getAll() {
        return entityManager.createQuery("select t from Tariff t order by t.name").getResultList();
    }

    @Override
    public void delete(Tariff tariff) {
        entityManager.remove(tariff);
    }

    @Override
    public Tariff getById(long id) {
        return entityManager.find(Tariff.class, id);
    }

    @Override
    public void update(Tariff tariff) {
        entityManager.merge(tariff);
    }
}
