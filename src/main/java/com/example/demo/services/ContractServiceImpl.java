package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    private final String BASE_NUMBER = "8920435";

    @Autowired
    ContractDao dao;

    @Override
    public Contract saveContract(Contract contract) {
        return dao.save(contract);
    }

    @Override
    public void connectTariff(Contract contract, Tariff tariff) {
        contract.setTariff(tariff);
        contract.getOption().clear();
        dao.update(contract);
    }

    @Override
    public Contract getContractById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Contract> getClientsContracts(long clientId) {
        return dao.getByClientId(clientId);
    }

    private String generateNumber() {
        String result;
        int suffix = (int) (Math.random() * 10000);
        if (suffix < 10)
            result = BASE_NUMBER + "000" + suffix;
        else if (suffix < 100)
            result = BASE_NUMBER + "00" + suffix;
        else if (suffix < 1000)
            result = BASE_NUMBER + "0" + suffix;
        else
            result = BASE_NUMBER + suffix;
        return result;
    }

    @Override
    public String getGeneratedNumber() {
        String number = generateNumber();
        while (!dao.isNumberUnique(number)) {
            number = generateNumber();
        }
        return number;
    }

    @Override
    public void connectOption(Contract contract, Option option) {
        contract.add(option);
        dao.update(contract);
    }

    @Override
    public void disconnectOption(Contract contract, Option option) {
        contract.delete(option);
        dao.update(contract);
    }

    @Override
    public List<Tariff> getAvailableTariffs(Client client) {
        return dao.getNotAddedToContractTariffsByClient(client.getId());
    }

    @Override
    public void blockByAdmin(Contract contract) {
        contract.setBlockedByAdmin(true);
        dao.update(contract);
    }

    @Override
    public void unblockByAdmin(Contract contract) {
        contract.setBlockedByAdmin(false);
        dao.update(contract);
    }

    @Override
    public void blockByClient(Contract contract) {
        contract.setBlockedByClient(true);
        dao.update(contract);
    }

    @Override
    public void unblockByClient(Contract contract) {
        contract.setBlockedByClient(false);
        dao.update(contract);
    }
}
