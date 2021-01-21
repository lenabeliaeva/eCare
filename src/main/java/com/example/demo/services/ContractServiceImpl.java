package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.dao.ContractDaoImpl;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private ContractDao dao = new ContractDaoImpl();

    @Override
    public void signContract(Contract contract) {
        dao.save(contract);
    }

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

    @Override
    public void connectOption(Contract contract, Option option) {
        dao.addOption(contract, option);
    }

    @Override
    public void disconnectOption(Contract contract, Option option) {
        dao.deleteOption(contract, option);
    }
}
