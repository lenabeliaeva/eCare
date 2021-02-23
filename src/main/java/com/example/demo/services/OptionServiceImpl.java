package com.example.demo.services;

import com.example.demo.dao.OptionDao;
import com.example.demo.dto.OptionDto;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
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

    /**
     * OptionDTO is used to serialize this list and to sent to client app.
     *
     * @return all options list
     */
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
        Option initialOption = dao.getById(option.getId());
        if (option.getTariff() == null) {
            option.setTariff(initialOption.getTariff());
        }
        if (option.getIncompatibleOptions() == null) {
            option.setIncompatibleOptions(initialOption.getIncompatibleOptions());
        }
        if (option.getDependentOptions() == null) {
            option.setDependentOptions(initialOption.getDependentOptions());
        }
        if (option.getContracts() == null) {
            option.setContracts(initialOption.getContracts());
        }
        dao.update(option);
        log.info("Option" + option.getName() + " info is updated");
    }

    /**
     * We have to check if the option is hold in a tariff. If it is the option is not deleted.
     * @param optionId
     */
    @Override
    @Transactional
    public void delete(long optionId) {
        Option option = dao.getById(optionId);
        if (option.getTariff().isEmpty() && option.getDependentOptions().isEmpty()
                && option.getIncompatibleOptions().isEmpty()) {
            dao.delete(option);
            log.info(option.getName() + " is deleted");
        } else {
            log.info(option.getName() + " can't be deleted as it is hold in a tariff or depends on other options");
        }
    }

    @Override
    @Transactional
    public List<Option> getAllNotAddedToTariff(Tariff tariff) {
        Set<Option> tariffOptions = tariff.getOptions();
        List<Option> options = dao.getAll();
        for (Option option :
                tariffOptions) {
            options.removeIf(o -> o.getId() == option.getId());
        }
        return options;
    }

    @Override
    @Transactional
    public Set<Option> getContractOptions(Contract contract) {
        if (contract != null) {
            Set<Option> contractOptions = contract.getOption();
            Tariff contractTariff = contract.getTariff();
            if (contractTariff != null) {
                Set<Option> tariffOptions = contractTariff.getOptions();
                Set<Option> options = new HashSet<>(contractOptions);
                options.addAll(tariffOptions);
                contract.setPrice(options.stream().mapToDouble(Option::getPrice).sum());
                contract.setConnectionCost(options.stream().mapToDouble(Option::getConnectionCost).sum());
                return options;
            }
        }
        return new HashSet<>();
    }

    @Override
    @Transactional
    public List<Option> getAllNotAddedToContractOptions(Contract contract) {
        Set<Option> contractOptions = getContractOptions(contract);
        List<Option> notAddedToContractOptions = dao.getAll();
        for (Option option :
                contractOptions) {
            notAddedToContractOptions.removeIf(o -> o.getId() == option.getId());
        }
        return notAddedToContractOptions;
    }

    /**
     * Options are incompatible in pairs.
     * If one of options depends on other they can't be incompatible.
     * @param firstOptionId
     * @param secondOptionId
     */
    @Override
    @Transactional
    public void addIncompatibleOption(long firstOptionId, long secondOptionId) {
        Option first = dao.getById(firstOptionId);
        Option second = dao.getById(secondOptionId);
        if (first.getDependentOptions().stream().noneMatch(o -> o.getId() == second.getId()) &&
        second.getDependentOptions().stream().noneMatch(o -> o.getId() == first.getId())) {
            first.addIncompatibleOption(second);
            second.addIncompatibleOption(first);
            dao.update(first);
            dao.update(second);
            log.info("Options " + first.getName() + " and " + second.getName() + " are made incompatible");
        } else {
            log.info(first.getName() + " and " + second.getName() + " are dependent and can't be incompatible");
        }
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
        log.info("Options " + first.getName() + " and " + second.getName() + " are made compatible");
    }

    @Override
    @Transactional
    public Set<Option> getIncompatibleOptions(long optionId) {
        Option option = dao.getById(optionId);
        return option.getIncompatibleOptions();
    }

    @Override
    @Transactional
    public List<Option> getCompatible(long optionId) {
        List<Option> compatible = dao.getAll();
        Set<Option> incompatible = dao.getById(optionId).getIncompatibleOptions();
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

    /**
     * First option depends on second while second stay independent from first.
     * If options are already incompatible they won't become dependent.
     * @param firstOptionId
     * @param secondOptionId
     */
    @Override
    @Transactional
    public void addDependentOption(long firstOptionId, long secondOptionId) {
        Option first = dao.getById(firstOptionId);
        Option second = dao.getById(secondOptionId);
        if (first.getIncompatibleOptions().stream().noneMatch(o -> o.getId() == second.getId())) {
            first.addDependentOption(second);
            dao.update(first);
            log.info("Option " + first.getName() + " now depends on " + second.getName());
        } else {
            log.info(first.getName() + " is incompatible with " + second.getName() + " and can't become dependent");
        }
    }

    @Override
    @Transactional
    public void deleteDependentOption(long firstOptionId, long secondOptionId) {
        Option first = dao.getById(firstOptionId);
        Option second = dao.getById(secondOptionId);
        first.deleteDependentOption(second);
        dao.update(first);
        log.info("Option " + first.getName() + " doesn't depends on " + second.getName());
    }

    @Override
    @Transactional
    public Set<Option> getDependentOptions(long optionId) {
        Option option = dao.getById(optionId);
        return option.getDependentOptions();
    }

    @Override
    @Transactional
    public List<Option> getIndependentOptions(long optionId) {
        List<Option> independent = dao.getAll();
        Set<Option> dependent = dao.getById(optionId).getDependentOptions();
        for (Option option :
                dependent) {
            independent.removeIf(o -> o.getId() == option.getId());
        }
        independent.removeIf(o -> o.getId() == optionId);
        return independent;
    }
}
