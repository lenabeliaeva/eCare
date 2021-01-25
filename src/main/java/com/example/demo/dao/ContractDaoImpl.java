package com.example.demo.dao;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
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
    public Contract save(Contract contract) {
        em.getTransaction().begin();
        em.persist(contract);
        em.getTransaction().commit();
        return contract;
    }

    @Override
    public void delete(Contract contract) {
        em.getTransaction().begin();
        em.remove(contract);
        em.getTransaction().commit();
    }

    @Override
    public void update(Contract contract) {
        em.getTransaction().begin();
        em.merge(contract);
        em.getTransaction().commit();
    }

    @Override
    public boolean isNumberUnique(String number) {
        try {
            em
                    .createQuery("select c from Contract c where c.number = :n")
                    .setParameter("n", number)
                    .getSingleResult();
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }

    @Override
    public void updateTariff(Contract contract, Tariff tariff) {
        em.getTransaction().begin();
        contract.setTariff(tariff);
        em.merge(contract);
        em.getTransaction().commit();
    }

    @Override
    public List<Tariff> getNotAddedToContractTariffs(long clientId) {
        return em.createQuery("select t from Tariff t left join t.contracts c where c.client.id <> :id or c.tariff.id is null")
                .setParameter("id", clientId)
                .getResultList();
    }

    @Override
    public void addOption(Contract contract, Option option) {
        em.getTransaction().begin();
        contract.add(option);
        em.merge(contract);
        em.getTransaction().commit();
    }

    @Override
    public void deleteOption(Contract contract, Option option) {
        em.getTransaction().begin();
        contract.delete(option);
        em.merge(contract);
        em.getTransaction().commit();
    }
}
