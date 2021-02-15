package com.example.demo.services;

import com.example.demo.models.Contract;

import java.util.List;

public interface ContractService {
    void saveContract(Contract contract, long clientId, long tariffId);

    void connectTariff(long contractId, long tariffId);

    Contract getContractById(long id);

    List<Contract> getClientsContracts(long id);

    String getGeneratedNumber();

    void connectOption(long contractId, long optionId);

    void disconnectOption(long contractId, long optionId);

    void blockByClient(long contractId);

    void unblockByClient(long contractId);

    void blockByAdmin(long contractId);

    void unblockByAdmin(long contractId);
}
