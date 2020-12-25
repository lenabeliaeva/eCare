package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tariffs")
@Data
public class Tariff {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;
}
