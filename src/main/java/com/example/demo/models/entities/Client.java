package com.example.demo.models.entities;

import com.example.demo.validators.PasswordMatches;
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
@PasswordMatches
@Table(name = "clients")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Name can't be empty")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "Last name can't be empty")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "passport")
    @NotBlank(message = "Passport can't be empty")
    @Pattern(regexp = "\\d{4}\\s\\d{6}", message = "Enter passport in format 0123 456789")
    private String passport;

    @Column(name = "address")
    @NotBlank(message = "Address can't be empty")
    private String address;

    @Column(name = "e_mail")
    @NotBlank(message = "Email can't be empty")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Does not look like an email")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password can't be empty")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}",
            message = "Password must contain at least one digit, " +
                    "one lowercase letter, one uppercase one and length must be not less than 8 symbols")
    private String password;

    @Transient
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
