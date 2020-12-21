package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tariffs")
@Data
public class Tariff {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;
}
