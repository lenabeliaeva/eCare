package com.example.demo.dto;

import com.example.demo.models.Client;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import lombok.Data;

import java.util.Set;

@Data
public class ContractDto {
    private long id;
    private String number;
    private boolean blockedByClient;
    private boolean blockedByAdmin;
    private double tariffPrice;
    private double connectionCost;
    private Client client;
    private Tariff tariff;
    private Set<Option> option;
}
