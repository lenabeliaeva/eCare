package com.example.demo.services;

import com.example.demo.dto.OptionDto;
import com.example.demo.models.Option;

import java.util.List;
import java.util.Set;

public interface OptionService {
    void add(Option option);

    List<OptionDto> getAll();

    List<Option> getAllForCertainTariff(long tariffId);

    Set<Option> getAllNotAddedToTariff(long tariffId);

    List<Option> getAllForCertainContract(long contractId, long tariffId);

    Set<Option> getAllNotAddedToContract(long contractId, long tariffId);

    Option getById(long optionId);

    void edit(Option option);

    void delete(Option option);
}
