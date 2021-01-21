package com.example.demo.services;

import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;

public interface ContractService {
    void signContract(Contract contract);
    Contract getContractById(long id);
    List<Contract> getClientsContracts(long id);
    void changeTariff(Contract contract, Tariff tariff);
    void connectOption(Contract contract, Option option);
    void disconnectOption(Contract contract, Option option);
}
