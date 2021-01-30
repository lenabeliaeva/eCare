package com.example.demo.dao;

import com.example.demo.models.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

@Repository
public class OptionDaoImpl implements OptionDao {

    private static final EntityManagerFactory emfObj;

    static {
        emfObj = Persistence.createEntityManagerFactory("eCare");
    }

    private final EntityManager entityManager = emfObj.createEntityManager();

    @Override
    public void add(Option option) {
        entityManager.getTransaction().begin();
        entityManager.persist(option);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Option> getAll() {
        return entityManager
                .createQuery("select o from Option o")
                .getResultList();
    }

    @Override
    public List<Option> getAllByTariffId(long tariffId) {
        return entityManager
                .createQuery("select o from Option o join o.tariff t where t.id = :id")
                .setParameter("id", tariffId)
                .getResultList();
    }

    @Override
    public List<Option> getAllNotAddedToTariff(long tariffId) {
        return entityManager
                .createQuery("select o from Option o left join o.tariff t where o not in (select o from Option o join o.tariff t where t.id = :id)")
                .setParameter("id", tariffId)
                .getResultList();
    }

    public List<Option> getAllByContractId(long contractId) {
        return entityManager
                .createQuery("select o from Option o join o.contracts c where c.id = :id")
                .setParameter("id", contractId)
                .getResultList();
    }

    @Override
    public List<Option> getAllNotAddedToContract(long contractId, long tariffId) {
        return entityManager
                .createQuery("select o from Option o left join o.contracts c left join o.tariff t where o not in (select o from Option o join o.contracts c where c.id = :id)")
                .setParameter("id", contractId)
                .getResultList();
    }

    public List<Option> getAllIncompatible(long optionId) {
        return entityManager
                .createQuery("select o from Option o join o.incompatibleOptions where o.id = :id")
                .setParameter("id", optionId)
                .getResultList();
    }

    @Override
    public void delete(Option option) {
        entityManager.getTransaction().begin();
        entityManager.remove(option);
        entityManager.getTransaction().commit();
    }

    @Override
    public Option getById(long id) {
        return entityManager.find(Option.class, id);
    }

    @Override
    public void update(Option option) {
        entityManager.getTransaction().begin();
        entityManager.merge(option);
        entityManager.getTransaction().commit();
    }
}
