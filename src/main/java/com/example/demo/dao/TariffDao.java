package com.example.demo.dao;

import com.example.demo.models.Tariff;

import java.util.List;

public interface TariffDao {
    void add(Tariff tariff);
    List<Tariff> getAll();

}
