package com.example.demo.dto;

import com.example.demo.models.Contract;
import com.example.demo.models.Role;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Data
public class ClientDto {
    private long id;
    private String name;
    private String lastName;
    private Date birthDate;
    private String passport;
    private String address;
    private String email;
    private String password;
    private String passwordConfirm;
    private Set<Contract> contracts;
    private Set<Role> roles;
}
