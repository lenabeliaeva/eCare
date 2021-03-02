package com.example.demo.dao.api;

import com.example.demo.models.entities.Client;

import java.util.List;

public interface ClientDao {
    void register(Client client);

    Client findByEmail(String email);

    Client findById(long id);

    List<Client> findByNumber(String number);

    Client findByPassport(String passport);

    List<Client> getAll();

    Client update(Client client);
}
