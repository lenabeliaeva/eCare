package com.example.demo.dao.impl;

import com.example.demo.dao.api.RoleDao;
import com.example.demo.models.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role getById(long id) {
        return em.find(Role.class, id);
    }
}
