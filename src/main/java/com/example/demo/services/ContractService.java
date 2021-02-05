package com.example.demo.services;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;

public interface ContractService {
    void saveContract(Contract contract);

    void connectTariff(Contract contract, Tariff tariff);

    Contract getContractById(long id);

    List<Contract> getClientsContracts(long id);

    String getGeneratedNumber();

    void connectOption(Contract contract, Option option);

    void disconnectOption(Contract contract, Option option);

    List<Tariff> getAvailableTariffs(Client client);

    void blockByClient(Contract contract);

    void unblockByClient(Contract contract);

    void blockByAdmin(Contract contract);

    void unblockByAdmin(Contract contract);
}
