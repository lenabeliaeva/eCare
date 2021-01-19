package com.example.demo.dao;

import com.example.demo.models.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class ContractDaoImpl implements ContractDao {

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("eCare");
    }

    private final EntityManager em = emf.createEntityManager();
}
