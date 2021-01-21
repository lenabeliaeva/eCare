package com.example.demo.dao;

import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;

public interface ContractDao {
    Contract getById(long id);
    List<Contract> getByClientId(long clientId);
    void save(Contract contract);
    void delete (Contract contract);
    void updateTariff(Contract contract, Tariff tariff);
    void addOption(Contract contract, Option option);
    void deleteOption(Contract contract, Option option);
}
