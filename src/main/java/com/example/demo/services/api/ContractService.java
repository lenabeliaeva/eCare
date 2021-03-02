package com.example.demo.services.api;

import com.example.demo.models.entities.Contract;

import java.util.List;

public interface ContractService {
    void saveContract(Contract contract, long clientId, long tariffId);

    Contract getContractById(long id);

    List<Contract> getClientsContracts(long id);

    String getGeneratedNumber();

    void disconnectOption(long contractId, long optionId);

    void blockByClient(long contractId);

    void unblockByClient(long contractId);

    void blockByAdmin(long contractId);

    void unblockByAdmin(long contractId);

    void terminateContract(long contractId);
}
