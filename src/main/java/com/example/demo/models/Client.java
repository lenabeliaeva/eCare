package com.example.demo.models;

import com.example.demo.validators.PasswordMatches;
import com.example.demo.validators.ValidEmail;
import com.example.demo.validators.ValidPassport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@PasswordMatches
@Table(name = "clients")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @ValidPassport
    @Column(name = "passport")
    @NotBlank(message = "Пасспорт не может быть пустым")
    private String passport;

    @Column(name = "address")
    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    @ValidEmail
    @Column(name = "e_mail")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Transient
    @NotBlank(message = "Пароль не может быть пустым")
    private String passwordConfirm;

    @OneToMany(mappedBy = "client")
    private Set<Contract> contracts;

    @ManyToMany
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
