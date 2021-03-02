package com.example.demo.dao.impl;

import com.example.demo.dao.api.ContractDao;
import com.example.demo.models.entities.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class ContractDaoImpl implements ContractDao {

    @PersistenceContext
    private EntityManager em;

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
        em.persist(contract);
        return contract;
    }

    @Override
    public void delete(Contract contract) {
        em.remove(contract);
    }

    @Override
    public void update(Contract contract) {
        em.merge(contract);
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
}
