package com.example.demo.services;

import com.example.demo.models.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> getContracts(int clientId);
}
