package com.example.demo.services;

import com.example.demo.dao.ClientDao;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;
import com.example.demo.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao dao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerNewClient(Client client) throws UserAlreadyExistsException {
        if (dao.findByEmail(client.getEmail()) != null) {
            throw new UserAlreadyExistsException("There is an account with this email: " + client.getEmail());
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        dao.register(client);
    }

    @Override
    public Client findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public Client findById(long id) {
        return dao.findById(id);
    }

    @Override
    public Client findByNumber(String number) {
        return dao.findByNumber(number);
    }

    @Override
    public List<Client> getAll() {
        return dao.getAll();
    }
}
