package com.example.demo.dao;

import com.example.demo.models.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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
            client = (Client) em
                    .createQuery("select c from Client c where c.email = :e")
                    .setParameter("e", email).setMaxResults(1)
                    .getSingleResult();
            return client;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Client findById(long id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> findByNumber(String number) {
        List<Client> clients = new LinkedList<>();
        try {
            clients = em
                    .createQuery("select c.client from Contract c where c.number = :n")
                    .setParameter("n", number)
                    .getResultList();
            return clients;
        } catch (NoResultException e) {
            return clients;
        }
    }

    @Override
    public Client findByPassport(String passport) {
        Client client;
        try {
            client = (Client) em
                    .createQuery("select c from Client c where c.passport = :p")
                    .setParameter("p", passport).setMaxResults(1)
                    .getSingleResult();
            return client;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Client> getAll() {
        return em.createQuery("select c from Client c").getResultList();
    }

    @Override
    public Client update(Client client) {
        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();
        return client;
    }
}
