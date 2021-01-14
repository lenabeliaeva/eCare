package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Client> clients;

    public Role(long id, String userRole) {
        this.id = id;
        this.name = userRole;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
