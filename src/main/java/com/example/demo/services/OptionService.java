package com.example.demo.services;

import com.example.demo.dto.OptionDto;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;
import java.util.Set;

public interface OptionService {
    void add(Option option);

    List<OptionDto> getAll();

    List<Option> getAllNotAddedToTariff(Tariff tariff);

    Set<Option> getContractOptions(Contract contract);

    List<Option> getAllNotAddedToContractOptions(Contract contract);

    Option getById(long optionId);

    void edit(Option option);

    void delete(long optionId);

    void addIncompatibleOption(long firstOptionId, long secondOptionId);

    void deleteIncompatibleOption(long firstOptionId, long secondOptionId);

    Set<Option> getIncompatibleOptions(long optionId);

    List<Option> getCompatible(long optionId);

    void addDependentOption(long firstOptionId, long secondOptionId);

    void deleteDependentOption(long firstOptionId, long secondOptionId);

    Set<Option> getDependentOptions(long optionId);

    List<Option> getIndependentOptions(long optionId);
}
