package com.example.demo.dao;

import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

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
                .createQuery("select o from Option o left join o.tariff t where t.id <> :id or t.id is null")
                .setParameter("id", tariffId)
                .getResultList();
    }

    public List<Option> getAllForCertainContract(long contractId) {
        return entityManager
                .createQuery("select o from Option o join o.contracts c where c.id = :id")
                .setParameter("id", contractId)
                .getResultList();
    }

    @Override
    public List<Option> getAllNotAddedToContract(long contractId, long tariffId) {
        return entityManager
                .createQuery("select o from Option o left join o.contracts c left join o.tariff t where (c.id <> :cId or c.id is null) and (t.id <> :tId or t.id is null)")
                .setParameter("cId", contractId)
                .setParameter("tId", tariffId)
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
