package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "tariffs")
@Getter
@Setter
public class Tariff {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Название тариффа не может быть пустым")
    private String name;

    @Column(name = "price")
    @Min(1)
    private double price;

    @ManyToMany
    @JoinTable(
            name = "options_tariffs",
            joinColumns = {@JoinColumn(name = "tariff_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    private Set<Option> options;

    public void add(Option o) {
        options.add(o);
    }

    public void delete(Option o) {
        options.removeIf(option -> option.getId() == o.getId());
    }

    @OneToMany(mappedBy = "tariff")
    private Set<Contract> contracts;
}
