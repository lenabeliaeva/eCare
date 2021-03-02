package com.example.demo.services.api;

import com.example.demo.dto.TariffDto;
import com.example.demo.exceptions.CantBeDeletedException;
import com.example.demo.models.entities.Option;
import com.example.demo.models.entities.Tariff;

import java.util.List;

public interface TariffService {
    void add(Tariff tariff);

    List<TariffDto> getAll();

    void delete(Tariff tariff) throws CantBeDeletedException;

    Tariff getById(long id);

    void edit(Tariff tariff);

    void addOption(Tariff tariff, Option option);

    void deleteOption(Tariff tariff, Option option);

    List<Tariff> getNotAddedToContractTariffs(long tariffId);
}
