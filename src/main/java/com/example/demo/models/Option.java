package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
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
    @NotBlank(message = "Option name can't be empty")
    private String name;

    @Column(name = "price")
    @Min(1)
    private double price;

    @Column(name = "connection_cost")
    @Min(1)
    private double connectionCost;

    @ManyToMany
    private Set<Tariff> tariff;

    @ManyToMany
    @JoinTable(
            name = "contracts_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "contract_id")}
    )
    private Set<Contract> contracts;

    @ManyToMany
    @JoinTable(
            name = "incompatible_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "incompat_option_id")}
    )
    private Set<Option> incompatibleOptions = new HashSet<>();

    public void addIncompatibleOption(Option option) {
        incompatibleOptions.add(option);
    }

    public void deleteIncompatibleOption(Option option) {
        incompatibleOptions.removeIf(option1 -> option1.getId() == option.getId());
    }
}
