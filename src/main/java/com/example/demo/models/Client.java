package com.example.demo.models;

import com.example.demo.validators.PasswordMatches;
import com.example.demo.validators.ValidEmail;
import com.example.demo.validators.ValidPassport;
import com.example.demo.validators.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
//@PasswordMatches
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

    @Column(name = "passport")
    @NotBlank(message = "Пасспорт не может быть пустым")
    @Pattern(regexp = "\\d{4}\\s\\d{6}", message = "Введите серию и номер в формате 0123 456789")
    private String passport;

    @Column(name = "address")
    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    @Column(name = "e_mail")
    @NotBlank(message = "Email не может быть пустым")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Не похоже на email")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}",
            message = "Пароль должен содержать хотя бы одну цифру, одну строчную букву, одну заглавную и длина должна быть не менее 8 символов")
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
