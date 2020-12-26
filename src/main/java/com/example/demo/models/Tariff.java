package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany
    private Set<Option> options;
}
