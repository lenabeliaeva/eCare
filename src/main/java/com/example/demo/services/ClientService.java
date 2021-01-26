package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;

import java.util.List;

public interface ClientService {
    void registerNewClient(Client client) throws UserAlreadyExistsException;

    Client findByEmail(String email);

    Client findById(long id);

    List<Client> findByNumber(String number);

    List<Client> getAll();

    Client getAuthorizedClient();

    String getRole();
}
