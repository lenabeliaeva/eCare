package com.example.demo.services.api;

import com.example.demo.dto.OptionDto;
import com.example.demo.exceptions.CantBeDeletedException;
import com.example.demo.exceptions.OptionsDependentException;
import com.example.demo.exceptions.OptionsIncompatibleException;
import com.example.demo.models.entities.Contract;
import com.example.demo.models.entities.Option;
import com.example.demo.models.entities.Tariff;

import java.util.List;
import java.util.Set;

public interface OptionService {
    void add(Option option);

    List<OptionDto> getAll();

    List<Option> getAllNotAddedToTariff(Tariff tariff);

    Set<Option> getContractOptions(Contract contract);

    Option getById(long optionId);

    void edit(Option option);

    void delete(long optionId) throws CantBeDeletedException;

    void addIncompatibleOption(long firstOptionId, long secondOptionId) throws OptionsDependentException;

    void deleteIncompatibleOption(long firstOptionId, long secondOptionId);

    Set<Option> getIncompatibleOptions(long optionId);

    List<Option> getCompatible(long optionId);

    void addDependentOption(long firstOptionId, long secondOptionId) throws OptionsIncompatibleException;

    void deleteDependentOption(long firstOptionId, long secondOptionId);

    Set<Option> getDependentOptions(long optionId);

    List<Option> getIndependentOptions(long optionId);
}
