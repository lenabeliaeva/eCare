package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.dao.ContractDaoImpl;
import com.example.demo.models.Contract;
import com.example.demo.models.Tariff;

import java.util.List;

public class ContractServiceImpl implements ContractService{
    private ContractDao dao = new ContractDaoImpl();

    @Override
    public Contract getContractById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Contract> getClientsContracts(long id) {
        return dao.getByClientId(id);
    }

    @Override
    public void changeTariff(Contract contract, Tariff tariff) {
        dao.updateTariff(contract, tariff);
    }
}
