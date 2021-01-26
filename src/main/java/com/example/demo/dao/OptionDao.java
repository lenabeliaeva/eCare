package com.example.demo.dao;

import com.example.demo.models.Option;

import java.util.List;
import java.util.Set;

public interface OptionDao {
    void add(Option option);
    List<Option> getAll();
    List<Option> getAllByTariffId(long tariffId);
    List<Option> getAllNotAddedToTariff(long tariffId);
    List<Option> getAllByContractId(long contractId);
    List<Option> getAllNotAddedToContract(long contractId, long tariffId);
    void delete(Option option);
    Option getById(long id);
    void update(Option option);
}
