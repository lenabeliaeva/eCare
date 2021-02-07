package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TariffDto {
    private long id;
    private String name;
    private double price;
    private Set<OptionDto> options;
}
