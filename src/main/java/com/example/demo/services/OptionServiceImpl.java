package com.example.demo.services;

import com.example.demo.dao.OptionDao;
import com.example.demo.dto.OptionDto;
import com.example.demo.models.Option;
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
    public List<Option> getAllForCertainTariff(long tariffId) {
        return dao.getAllByTariffId(tariffId);
    }

    @Override
    @Transactional
    public Set<Option> getAllNotAddedToTariff(long tariffId) {
        List<Option> options = dao.getAllNotAddedToTariff(tariffId);
        return new HashSet<>(options);
    }

    @Override
    @Transactional
    public List<Option> getAllForCertainContract(long contractId, long tariffId) {
        List<Option> contractsAndTariffsOptions = new LinkedList<>(dao.getAllByTariffId(tariffId));
        contractsAndTariffsOptions.addAll(dao.getAllByContractId(contractId));
        return contractsAndTariffsOptions;
    }

    @Override
    @Transactional
    public Set<Option> getAllNotAddedToContract(long contractId, long tariffId) {
        List<Option> options = dao.getAllNotAddedToContract(contractId, tariffId);
        options.removeAll(dao.getAllByTariffId(tariffId));
        return new HashSet<>(options);
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
    public List<OptionDto> getCompatible(long optionId) {
        List<OptionDto> compatible = getAll();
        Set<Option> incompatible = getById(optionId).getIncompatibleOptions();
        for (Option o:
             incompatible) {
            long currentOptionId = o.getId();
            compatible.removeIf(optionDto -> optionDto.getId() == currentOptionId);
        }
        compatible.removeIf(o -> o.getId() == optionId);
        return compatible;
    }

}
