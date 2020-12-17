package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.dao.ContractDaoImpl;
import com.example.demo.models.Contract;

import java.util.List;

public class ContractServiceImpl implements ContractService {

    private final ContractDao contractDao = new ContractDaoImpl();

    @Override
    public List<Contract> getContracts(int clientId) {
        return contractDao.getContractsByClientId(clientId);
    }
}
