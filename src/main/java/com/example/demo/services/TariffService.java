package com.example.demo.services;

import com.example.demo.models.Tariff;

import java.util.List;

public interface TariffService {
    void add(Tariff tariff);
    List<Tariff> getAll();
    void delete(Tariff tariff);
    Tariff getById(long id);
    void edit(Tariff tariff);
}
