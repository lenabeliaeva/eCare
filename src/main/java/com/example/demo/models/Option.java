package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "options")
public class Option {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Название опции должно быть не пустым")
    private String name;

    @Column(name = "price")
    @Min(1)
    private double price;

    @Column(name = "connection_cost")
    @Min(1)
    private double connectionCost;

    @ManyToMany
    private Set<Tariff> tariff;
}
