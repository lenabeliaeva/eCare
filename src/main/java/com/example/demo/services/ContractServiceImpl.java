package com.example.demo.services;

import com.example.demo.dao.ClientDao;
import com.example.demo.dao.ContractDao;
import com.example.demo.dao.OptionDao;
import com.example.demo.dao.TariffDao;
import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    private final Random random = new Random();
    private static final String BASE_NUMBER = "8920435";

    @Autowired
    ContractDao dao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    TariffDao tariffDao;
    @Autowired
    OptionDao optionDao;

    @Override
    public void saveContract(Contract contract, long clientId, long tariffId) {
        Client client = clientDao.findById(clientId);
        Tariff tariff = tariffDao.getById(tariffId);
        contract.setClient(client);
        contract.setTariff(tariff);
        contract.setTariffPrice(tariff.getPrice());
        contract.setConnectionCost(calcConnectionCost(tariff));
        dao.save(contract);
    }

    @Override
    public void connectTariff(long contractId, long tariffId) {
        Contract contract = dao.getById(contractId);
        Tariff tariff = tariffDao.getById(tariffId);
        contract.setTariff(tariff);
        contract.setTariffPrice(tariff.getPrice());
        contract.getOption().clear();
        contract.setConnectionCost(calcConnectionCost(tariff));
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

    @Override
    public String getGeneratedNumber() {
        String number = generateNumber();
        while (!dao.isNumberUnique(number)) {
            number = generateNumber();
        }
        return number;
    }

    @Override
    public void connectOption(long contractId, long optionId) {
        Contract contract = dao.getById(contractId);
        Option option = optionDao.getById(optionId);
        contract.add(option);
        double newPrice = contract.getTariffPrice() + option.getPrice();
        contract.setTariffPrice(newPrice);
        double newConnectionCost = contract.getConnectionCost() + option.getConnectionCost();
        contract.setConnectionCost(newConnectionCost);
        dao.update(contract);
    }

    @Override
    public void disconnectOption(long contractId, long optionId) {
        Contract contract = dao.getById(contractId);
        Option option = optionDao.getById(optionId);
        if (contract.delete(option)) {
            double newPrice = contract.getTariffPrice() - option.getPrice();
            contract.setTariffPrice(newPrice);
            double newConnectionCost = contract.getConnectionCost() - option.getConnectionCost();
            contract.setConnectionCost(newConnectionCost);
            dao.update(contract);
        }
    }

    @Override
    public void blockByAdmin(long contractId) {
        Contract contract = dao.getById(contractId);
        contract.setBlockedByAdmin(true);
        dao.update(contract);
    }

    @Override
    public void unblockByAdmin(long contractId) {
        Contract contract = dao.getById(contractId);
        contract.setBlockedByAdmin(false);
        dao.update(contract);
    }

    @Override
    public void blockByClient(long contractId) {
        Contract contract = dao.getById(contractId);
        contract.setBlockedByClient(true);
        dao.update(contract);
    }

    @Override
    public void unblockByClient(long contractId) {
        Contract contract = dao.getById(contractId);
        contract.setBlockedByClient(false);
        dao.update(contract);
    }

    private String generateNumber() {
        String result;
        int suffix = random.nextInt(9999) + 1;
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

    private double calcConnectionCost(Tariff tariff) {
        double contractConnectionCost = 0;
        for (Option o:
                tariff.getOptions()) {
            contractConnectionCost += o.getConnectionCost();
        }
        return contractConnectionCost;
    }
}
