package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.dao.api.ContractDao;
import com.example.demo.dao.api.OptionDao;
import com.example.demo.models.entities.Client;
import com.example.demo.models.entities.Contract;
import com.example.demo.models.entities.Option;
import com.example.demo.models.entities.Tariff;
import com.example.demo.services.impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractDao contractDao;

    @Mock
    private OptionDao optionDao;

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract;
    private Contract anotherContract;
    List<Contract> clientContracts;
    private Option option;

    @BeforeEach
    public void setup() {
        contract = new Contract();
        contract.setId(1L);
        anotherContract = new Contract();
        anotherContract.setId(2L);
        Client client = new Client();
        client.setId(1L);
        contract.setClient(client);
        clientContracts = new LinkedList<>();
        clientContracts.add(contract);
        option = new Option();
        option.setId(1L);
        option.setPrice(200);
        option.setConnectionCost(100);
    }

    @Test
    void shouldGetById() {
        when(contractDao.getById(1L)).thenReturn(contract);
        Contract found = contractService.getContractById(1L);
        assertEquals(contract, found);
    }

    @Test
    void shouldNotGetById() {
        when(contractDao.getById(2L)).thenReturn(anotherContract);
        Contract found = contractService.getContractById(2L);
        assertNotEquals(contract, found);
    }

    @Test
    void shouldGetByClientId() {
        when(contractDao.getByClientId(1L)).thenReturn(clientContracts);
        List<Contract> found = contractService.getClientsContracts(1L);
        assertEquals(clientContracts, found);
    }

    @Test
    void shouldNotGetByClientId() {
        when(contractDao.getByClientId(2L)).thenReturn(new LinkedList<>());
        List<Contract> found = contractService.getClientsContracts(2L);
        assertNotEquals(clientContracts, found);
    }

    @Test
    void shouldDisconnectOption() {
        when(contractDao.getById(1L)).thenReturn(contract);
        when(optionDao.getById(1L)).thenReturn(option);
        doNothing().when(contractDao).update(isA(Contract.class));
        contract.add(option);
        contract.setPrice(200);
        contract.setConnectionCost(100);
        contractService.disconnectOption(1L, 1L);
        Contract expected = new Contract();
        assertEquals(expected.getPrice(), contract.getPrice());
        assertEquals(expected.getConnectionCost(), contract.getConnectionCost());
        assertEquals(expected.getOption(), contract.getOption());
    }

    @Test
    void shouldNotDisconnectOption() {
        when(contractDao.getById(1L)).thenReturn(contract);
        when(optionDao.getById(1L)).thenReturn(option);
        Tariff tariff = new Tariff();
        tariff.add(option);
        contract.setTariff(tariff);
        contractService.disconnectOption(1L, 1L);
        Contract expected = new Contract();
        assertEquals(expected.getOption(), contract.getOption());
    }
}
