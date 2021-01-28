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
    @NotBlank(message = "Номер телефона не может быть пустым")
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
    private Set<Option> option;

    public void add(Option o) {
        if(option == null){
            option = new HashSet<>();
        }
        this.option.add(o);
    }

    public boolean delete(Option o) {
        return this.option.removeIf(option -> option.getId() == o.getId());
    }
}
