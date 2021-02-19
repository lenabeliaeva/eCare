package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "number")
    @NotBlank(message = "Phone number can't be empty")
    private String number;

    @Column(name = "blocked_by_client")
    private boolean blockedByClient = false;

    @Column(name = "blocked_by_admin")
    private boolean blockedByAdmin = false;

    @Column(name = "tariff_price")
    private double tariffPrice;

    @Column(name = "connection_cost")
    private double connectionCost;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToMany
    private Set<Option> option = new HashSet<>();

    public void add(Option o) {
        this.option.add(o);
    }

    public boolean delete(Option o) {
        return this.option.removeIf(it -> it.getId() == o.getId());
    }
}
