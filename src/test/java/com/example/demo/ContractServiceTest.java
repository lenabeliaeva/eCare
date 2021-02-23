package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.dao.ContractDao;
import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.services.ContractServiceImpl;
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

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract;
    private Contract anotherContract;
    List<Contract> clientContracts;

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
}
