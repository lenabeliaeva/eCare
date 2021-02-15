package com.example.demo.services;

import com.example.demo.dao.TariffDao;
import com.example.demo.dto.TariffDto;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
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
    public Tariff add(Tariff tariff) {
        Tariff savedTariff = tariffDao.add(tariff);
        try {
            mqService.sendMessage("Tariff " + tariff.getName() + " is created");
        } catch (IOException | TimeoutException e) {
            log.warn("Couldn't send message. " + e.getMessage());
        }
        return savedTariff;
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

    @Override
    @Transactional
    public boolean delete(Tariff tariff) {
        boolean isSuccessful = tariffDao.delete(tariff);
        if (isSuccessful) {
            try {
                mqService.sendMessage("Tariff " + tariff.getName() + " is deleted");
            } catch (IOException | TimeoutException e) {
                log.warn("Couldn't send message. " + e.getMessage());
            }
        }
        return isSuccessful;
    }

    @Override
    @Transactional
    public Tariff getById(long id) {
        return tariffDao.getById(id);
    }

    @Override
    @Transactional
    public void edit(Tariff tariff) {
        tariffDao.edit(tariff);
        try {
            mqService.sendMessage("Tariff " + tariff.getName() + " is updated");
        } catch (IOException | TimeoutException e) {
            log.warn("Couldn't send message. " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void addOption(Tariff tariff, Option option) {
        tariff.setPrice(tariff.getPrice() + option.getPrice());
        tariff.add(option);
        tariffDao.edit(tariff);
        try {
            mqService.sendMessage("Tariff " + tariff.getName() + " is updated");
        } catch (IOException | TimeoutException e) {
            log.warn("Couldn't send message. " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteOption(Tariff tariff, Option option) {
        if (tariff.getOptions().size() > 1) {
            tariff.delete(option);
            tariff.setPrice(tariff.getPrice() - option.getPrice());
            tariffDao.edit(tariff);
            try {
                mqService.sendMessage("Tariff " + tariff.getName() + " is updated");
            } catch (IOException | TimeoutException e) {
                log.warn("Couldn't send message. " + e.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public List<Tariff> getNotAddedToContractTariffs(long tariffId) {
        return tariffDao.getNotAddedToContractTariffs(tariffId);
    }
}
