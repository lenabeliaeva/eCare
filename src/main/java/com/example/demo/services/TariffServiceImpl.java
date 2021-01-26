package com.example.demo.services;

import com.example.demo.dao.TariffDao;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    @Autowired
    TariffDao tariffDao;

    @Override
    @Transactional
    public Tariff add(Tariff tariff) {
        return tariffDao.add(tariff);
    }

    @Override
    @Transactional
    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }

    @Override
    @Transactional
    public boolean delete(Tariff tariff) {
        return tariffDao.delete(tariff);
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
    }

    @Override
    @Transactional
    public void addOption(Tariff tariff, Option option) {
        tariff.setPrice(tariff.getPrice() + option.getPrice());
        tariffDao.addOption(tariff, option);
    }

    @Override
    @Transactional
    public boolean deleteOption(Tariff tariff, Option option) {
        if (tariff.getOptions().size() > 1){
            tariff.delete(option);
            tariff.setPrice(tariff.getPrice() - option.getPrice());
            tariffDao.edit(tariff);
            return true;
        } else {
             return false;
        }
    }

    @Override
    @Transactional
    public List<Tariff> getNotAddedToContractTariffs(Tariff tariff) {
        return tariffDao.getNotAddedToContractTariffs(tariff.getId());
    }
}
