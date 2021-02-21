package com.example.demo.services;

import com.example.demo.dao.TariffDao;
import com.example.demo.dto.TariffDto;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Log4j
@Service
public class TariffServiceImpl implements TariffService {

    @Autowired
    TariffDao tariffDao;

    @Autowired
    MessageQueueService mqService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void add(Tariff tariff) {
        tariffDao.add(tariff);
        try {
            mqService.sendMessage("Tariff " + tariff.getName() + " is created");
        } catch (IOException | TimeoutException e) {
            log.warn("Couldn't send message. " + e.getMessage());
        }
        log.info("Tariff " + tariff.getName() + " is created");
    }

    @Override
    @Transactional
    public List<TariffDto> getAll() {
        List<Tariff> tariffs = tariffDao.getAll();
        return tariffs
                .stream()
                .map(it -> modelMapper.map(it, TariffDto.class))
                .collect(Collectors.toList());
    }

    /**
     * If tariff is not contained in any contract it is deleted and message to the client app is sent.
     * @param tariff to be deleted
     */
    @Override
    @Transactional
    public void delete(Tariff tariff) {
        try {
            tariffDao.delete(tariff);
            sendMessage("Tariff " + tariff.getName() + "is deleted");
            log.info("Tariff " + tariff.getName() + " is deleted");
        } catch (RollbackException e) {
            log.info("Tariff " + tariff.getName() + " can't be deleted as it is contained in contract");
        }
    }

    @Override
    @Transactional
    public Tariff getById(long id) {
        return tariffDao.getById(id);
    }

    @Override
    @Transactional
    public void edit(Tariff tariff) {
        Tariff initial = getById(tariff.getId());
        if (tariff.getOptions() == null)
            tariff.setOptions(initial.getOptions());
        tariffDao.update(tariff);
        sendMessage("Tariff " + tariff.getName() + " is updated");
        log.info("Tariff " + tariff.getName() + " is updated");
    }

    /**
     * There is a tariff object in a session and it will be added after submitting.
     * @param tariff from session
     * @param option
     */
    @Override
    @Transactional
    public void addOption(Tariff tariff, Option option) {
        if (option.isCompatibleWith(tariff.getOptions()) && option.isDependentFrom(tariff.getOptions())) {
            tariff.setPrice(tariff.getPrice() + option.getPrice());
            tariff.add(option);
            log.info("Option " + option.getName() + " is going to be added to " + tariff.getName());
        }
    }

    /**
     * There is a tariff object in a session and it will be deleted after submitting.
     * @param tariff
     * @param option
     */
    @Override
    @Transactional
    public void deleteOption(Tariff tariff, Option option) {
        if (tariff.getOptions().size() > 1) {
            tariff.delete(option);
            tariff.setPrice(tariff.getPrice() - option.getPrice());
            log.info("Option " + option.getName() + " is going to be deleted from " + tariff.getName());
        }
    }

    @Override
    @Transactional
    public List<Tariff> getNotAddedToContractTariffs(long tariffId) {
        List<Tariff> tariffs = tariffDao.getAll();
        tariffs.removeIf(t -> t.getId() == tariffId);
        return tariffs;
    }

    private void sendMessage(String message) {
        try {
            mqService.sendMessage(message);
        } catch (IOException | TimeoutException e) {
            log.warn("Couldn't send message. " + e.getMessage());
        }
    }
}
