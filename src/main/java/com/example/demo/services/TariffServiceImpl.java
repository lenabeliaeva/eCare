package com.example.demo.services;

import com.example.demo.dao.TariffDao;
import com.example.demo.dao.TariffDaoImpl;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    private final TariffDao tariffDao = new TariffDaoImpl();

    @Override
    @Transactional
    public void add(Tariff tariff) {
        tariffDao.add(tariff);
    }

    @Override
    @Transactional
    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }

    @Override
    @Transactional
    public List<Tariff> getTariffsByOptionId(long optionId) {
        return tariffDao.getAllByOptionId(optionId);
    }

    @Override
    @Transactional
    public void delete(Tariff tariff) {
        tariffDao.delete(tariff);
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
        tariffDao.addOption(tariff, option);
    }

    @Override
    @Transactional
    public void deleteOption(Tariff tariff, Option option) {
        tariffDao.deleteOption(tariff, option);
    }
}
