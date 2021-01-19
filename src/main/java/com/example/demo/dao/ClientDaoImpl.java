package com.example.demo.dao;

import com.example.demo.models.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class ClientDaoImpl implements ClientDao {

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("eCare");
    }

    private final EntityManager em = emf.createEntityManager();

    @Override
    public void register(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    @Override
    public Client findByEmail(String email) {
        Client client;
        try {
            client = (Client) em.createQuery("select c from Client c where c.email = :e").setParameter("e", email).setMaxResults(1).getSingleResult();
            return client;
        } catch (NoResultException e) {
            return null;
        }
    }
}
