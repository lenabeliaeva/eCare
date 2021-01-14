package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;

public interface ClientService {
    void registerNewClient(Client client) throws UserAlreadyExistsException;
    Client findByEmail(String email);
}
