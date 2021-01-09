package com.example.demo.dao;

import com.example.demo.models.Option;
import java.util.List;

public interface OptionDao {
    void add(Option option);
    List<?> getAll();
    List<?> getAllByTariffId(long tariffId);
    void delete(Option option);
    Option getById(long id);
    void update(Option option);
}
