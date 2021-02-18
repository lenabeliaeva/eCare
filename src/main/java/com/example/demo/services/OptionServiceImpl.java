package com.example.demo.services;

import com.example.demo.dao.OptionDao;
import com.example.demo.dto.OptionDto;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    OptionDao dao;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void add(Option option) {
        dao.add(option);
    }

    @Override
    @Transactional
    public List<OptionDto> getAll() {
        List<Option> options = dao.getAll();
        return options
                .stream()
                .map(it -> modelMapper.map(it, OptionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Option getById(long optionId) {
        return dao.getById(optionId);
    }

    @Override
    @Transactional
    public void edit(Option option) {
        Option initialOption = getById(option.getId());
        if (option.getTariff() == null) {
            option.setTariff(initialOption.getTariff());
        }
        if (option.getIncompatibleOptions() == null) {
            option.setIncompatibleOptions(initialOption.getIncompatibleOptions());
        }
        if (option.getContracts() == null) {
            option.setContracts(initialOption.getContracts());
        }
        dao.update(option);
    }

    @Override
    @Transactional
    public void delete(long optionId) {
        Option option = dao.getById(optionId);
        dao.delete(option);
    }

    @Override
    @Transactional
    public List<Option> getAllNotAddedToTariff(Tariff tariff) {
        Set<Option> tariffOptions = tariff.getOptions();
        List<Option> options = dao.getAll();
        for (Option option:
                tariffOptions) {
            options.removeIf(o -> o.getId() == option.getId());
        }
        return options;
    }

    @Override
    @Transactional
    public Set<Option> getContractOptions(Contract contract) {
        Set<Option> contractOptions = contract.getOption();
        Set<Option> tariffOptions = contract.getTariff().getOptions();
        Set<Option> options = new HashSet<>(contractOptions);
        options.addAll(tariffOptions);
        return options;
    }

    @Override
    @Transactional
    public List<Option> getAllNotAddedToContractOptions(Contract contract) {
        Set<Option> contractOptions = getContractOptions(contract);
        List<Option> notAddedToContractOptions = dao.getAll();
        for (Option option:
                contractOptions) {
            notAddedToContractOptions.removeIf(o -> o.getId() == option.getId());
        }
        return notAddedToContractOptions;
    }

    @Override
    @Transactional
    public void addIncompatibleOption(long firstOptionId, long secondOptionId) {
        Option first = getById(firstOptionId);
        Option second = getById(secondOptionId);
        first.addIncompatibleOption(second);
        second.addIncompatibleOption(first);
        dao.update(first);
        dao.update(second);
    }

    @Override
    @Transactional
    public void deleteIncompatibleOption(long firstOptionId, long secondOptionId) {
        Option first = getById(firstOptionId);
        Option second = getById(secondOptionId);
        first.deleteIncompatibleOption(second);
        second.deleteIncompatibleOption(first);
        dao.update(first);
        dao.update(second);
    }

    @Override
    @Transactional
    public Set<Option> getIncompatibleOptions(long optionId) {
        Option option = getById(optionId);
        return option.getIncompatibleOptions();
    }

    @Override
    @Transactional
    public List<Option> getCompatible(long optionId) {
        List<Option> compatible = dao.getAll();
        Set<Option> incompatible = getById(optionId).getIncompatibleOptions();
        if (incompatible != null) {
            for (Option o :
                    incompatible) {
                long currentOptionId = o.getId();
                compatible.removeIf(option -> option.getId() == currentOptionId);
            }
        }
        compatible.removeIf(o -> o.getId() == optionId);
        return compatible;
    }

}
