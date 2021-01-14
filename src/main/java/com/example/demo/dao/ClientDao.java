package com.example.demo.dao;

import com.example.demo.models.Client;

public interface ClientDao {
    void register(Client client);
    Client findByEmail(String email);
}
