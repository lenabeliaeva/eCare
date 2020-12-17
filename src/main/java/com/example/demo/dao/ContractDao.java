package com.example.demo.dao;

import com.example.demo.models.Contract;

import java.util.List;

public interface ContractDao {
    List<Contract> getContractsByClientId(int clientId);
}
