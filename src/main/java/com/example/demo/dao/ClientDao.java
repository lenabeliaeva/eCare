package com.example.demo.dao;

import com.example.demo.models.Client;

import java.util.List;

public interface ClientDao {
    void register(Client client);

    Client findByEmail(String email);

    Client findById(long id);

    List<Client> findByNumber(String number);

    List<Client> getAll();
}
