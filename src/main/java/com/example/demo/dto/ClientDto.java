package com.example.demo.dto;

import lombok.Data;

import java.sql.Date;

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
}
