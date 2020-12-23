package com.example.demo.dao;

import com.example.demo.models.Contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ContractDaoImpl implements ContractDao{

    @Override
    public List<Contract> getContractsByClientId(int clientId) {
        List<Contract> contracts = new LinkedList<>();
//        Contract contract;
//        try {
//            PreparedStatement statement = connection.prepareStatement("select * from contracts where client_id = ?");
//            statement.setInt(1, clientId);
//            ResultSet resultSet = statement.executeQuery();
//            contract = new Contract();
//            while (resultSet.next()) {
//                contract.setId(resultSet.getInt("id"));
//                contract.setNumber(resultSet.getString("number"));
//                contract.setClientId(resultSet.getInt("client_id"));
//                contract.setTariffId(resultSet.getInt("tariff_id"));
//                contract.setOptionsId(resultSet.getInt("option_id"));
//                contracts.add(contract);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return contracts;
    }
}
