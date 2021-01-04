package com.example.demo.dao;

import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;

public interface TariffDao {
    void add(Tariff tariff);
    List<Tariff> getAll();
    void delete(Tariff tariff);
    Tariff getById(long id);
    void edit(Tariff tariff);
    void addOption(Tariff tariff, Option option);
    void deleteOption(Tariff tariff, Option option);
}
