package com.example.demo.dao;

import com.example.demo.models.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClientDaoImpl implements ClientDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public void register(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    @Override
    public Client findByEmail(String email) {
        return em.find(Client.class, email);
    }
}
