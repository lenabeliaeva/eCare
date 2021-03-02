package com.example.demo.dao.api;

import com.example.demo.models.entities.Contract;

import java.util.List;

public interface ContractDao {
    Contract getById(long id);

    List<Contract> getByClientId(long clientId);

    Contract save(Contract contract);

    void delete(Contract contract);

    void update(Contract contract);

    boolean isNumberUnique(String number);
}
