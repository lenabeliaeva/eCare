package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tariffs")
public class Tariff implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Tariff name can't be empty")
    private String name;

    @Column(name = "price")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "options_tariffs",
            joinColumns = {@JoinColumn(name = "tariff_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    private Set<Option> options;

    public void add(Option o) {
        if (options == null) {
            options = new HashSet<>();
        }
        options.add(o);
    }

    public void delete(Option o) {
        options.removeIf(option -> option.getId() == o.getId());
    }

    @OneToMany(mappedBy = "tariff")
    private Set<Contract> contracts;
}
