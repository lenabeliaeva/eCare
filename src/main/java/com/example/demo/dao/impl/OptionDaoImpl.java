package com.example.demo.dao.impl;

import com.example.demo.dao.api.OptionDao;
import com.example.demo.models.entities.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OptionDaoImpl implements OptionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Option option) {
        entityManager.persist(option);
    }

    @Override
    public List<Option> getAll() {
        return entityManager
                .createQuery("select o from Option o")
                .getResultList();
    }

    @Override
    public void delete(Option option) {
        entityManager.remove(option);
    }

    @Override
    public Option getById(long id) {
        return entityManager.find(Option.class, id);
    }

    @Override
    public void update(Option option) {
        entityManager.merge(option);
    }
}
