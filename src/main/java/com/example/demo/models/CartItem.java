package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CartItem {
    private double price;
    private Tariff tariff;
    private Contract contract;
    private Set<Option> options;
    private double connectionCost;

    public void addOption(Option option) {
        if (options == null) {
            options = new HashSet<>();
        }
        options.add(option);
    }

    public void deleteOption(Option o) {
        options.removeIf(option -> option.getId() == o.getId());
    }
}
