package com.example.demo.services;

import com.example.demo.models.Option;

import java.util.List;

public interface OptionService {
    void add(Option option);
    List<Option> getAll();
    List<Option> getAllForCertainTariff(long tariffId);
    Option getById(long optionId);
    void edit(Option option);
    void delete(Option option);
}