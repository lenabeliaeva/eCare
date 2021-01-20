package com.example.demo.services;

import com.example.demo.models.Contract;
import com.example.demo.models.Tariff;

import java.util.List;

public interface ContractService {
    Contract getContractById(long id);
    List<Contract> getClientsContracts(long id);
    void changeTariff(Contract contract, Tariff tariff);
}
