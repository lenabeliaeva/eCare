package com.example.demo.dao;

import com.example.demo.models.Tariff;

import java.util.List;

public interface TariffDao {
    Tariff add(Tariff tariff);

    List<Tariff> getAll();

    void delete(Tariff tariff);

    Tariff getById(long id);

    void update(Tariff tariff);
}
