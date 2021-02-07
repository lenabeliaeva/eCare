package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OptionDto implements Serializable {
    private long id;
    private String name;
    private double price;
    private double connectionCost;
}
