package com.example.demo.dao;

import com.example.demo.models.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class TariffDaoImpl implements TariffDao {

    private static final EntityManagerFactory emfObj;

    static {
        emfObj = Persistence.createEntityManagerFactory("eCare");
    }

    private final EntityManager entityManager = emfObj.createEntityManager();

    @Override
    public Tariff add(Tariff tariff) {
        entityManager.getTransaction().begin();
        entityManager.persist(tariff);
        entityManager.getTransaction().commit();
        return tariff;
    }

    @Override
    public List<Tariff> getAll() {
        return entityManager.createQuery("select t from Tariff t order by t.name").getResultList();
    }

    @Override
    public boolean delete(Tariff tariff) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(tariff);
            entityManager.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            return false;
        }
    }

    @Override
    public Tariff getById(long id) {
        return entityManager.find(Tariff.class, id);
    }

    @Override
    public void edit(Tariff tariff) {
        entityManager.getTransaction().begin();
        entityManager.merge(tariff);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Tariff> getNotAddedToContractTariffs(long tariffId) {
        return entityManager
                .createQuery("select t from Tariff t where t.id <> :id")
                .setParameter("id", tariffId)
                .getResultList();
    }
}
