package com.example.demo.dao;

import com.example.demo.models.Contract;

import java.util.List;

public interface ContractDao {
    Contract getById(long id);
    List<Contract> getByClientId(long clientId);
    void save(Contract contract);
    void update(Contract contract);
    void delete (Contract contract);
}
