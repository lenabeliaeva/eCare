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
    private Set<Option> incompatibleOptions;

    @OneToMany
    @JoinTable(
            name = "dependent_options",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "dependent_option_id")}
    )
    private Set<Option> dependentOptions;

    public void addIncompatibleOption(Option option) {
        if (incompatibleOptions == null) {
            incompatibleOptions = new HashSet<>();
        }
        incompatibleOptions.add(option);
    }

    public void deleteIncompatibleOption(Option option) {
        incompatibleOptions.removeIf(option1 -> option1.getId() == option.getId());
    }

    public void addDependentOption(Option option) {
        if (dependentOptions == null) {
            dependentOptions = new HashSet<>();
        }
        dependentOptions.add(option);
    }

    public void deleteDependentOption(Option option) {
        dependentOptions.removeIf(o -> o.getId() == option.getId());
    }

    public boolean isDependentFrom(Set<Option> alreadyAddedOptions) {
        for (Option option :
                alreadyAddedOptions) {
            if (option.getDependentOptions()
                            .stream()
                            .anyMatch(o -> o.getId() == this.getId())) {
                return true;
            }
        }
        return false;
    }
}
