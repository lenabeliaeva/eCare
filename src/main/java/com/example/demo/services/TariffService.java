package com.example.demo.services;

import com.example.demo.dto.TariffDto;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;

import java.util.List;

public interface TariffService {
    void add(Tariff tariff);

    List<TariffDto> getAll();

    void delete(Tariff tariff);

    Tariff getById(long id);

    void edit(Tariff tariff);

    void addOption(Tariff tariff, Option option);

    void deleteOption(Tariff tariff, Option option);

    List<Tariff> getNotAddedToContractTariffs(long tariffId);
}
