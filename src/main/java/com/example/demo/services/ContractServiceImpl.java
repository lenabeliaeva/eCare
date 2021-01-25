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
import java.util.Set;

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
    public void updateContract(Contract contract) {
        dao.update(contract);
    }

    @Override
    public void connectTariff(Contract contract, Tariff tariff) {
        dao.updateTariff(contract, tariff);
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
    public void changeTariff(Contract contract, Tariff tariff) {
        dao.updateTariff(contract, tariff);
    }

    @Override
    public void connectOption(Contract contract, Option option) {
        dao.addOption(contract, option);
    }

    @Override
    public void disconnectOption(Contract contract, Option option) {
        dao.deleteOption(contract, option);
    }

    @Override
    public List<Tariff> getAvailableTariffs(Client client) {
        return dao.getNotAddedToContractTariffs(client.getId());
    }
}
