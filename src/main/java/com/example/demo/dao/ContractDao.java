package com.example.demo.dao;

import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;

public interface ContractDao {
    Contract getById(long id);

    List<Contract> getByClientId(long clientId);

    Contract save(Contract contract);

    void delete(Contract contract);

    void update(Contract contract);

    boolean isNumberUnique(String number);

    List<Tariff> getNotAddedToContractTariffs(long clientId);
}
