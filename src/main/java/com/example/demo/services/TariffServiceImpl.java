package com.example.demo.services;

import com.example.demo.dao.TariffDao;
import com.example.demo.dao.TariffDaoImpl;
import com.example.demo.models.Tariff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    private final TariffDao tariffDao = new TariffDaoImpl();

    @Override
    public void add(Tariff tariff) {
        tariffDao.add(tariff);
    }

    @Override
    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }
}
