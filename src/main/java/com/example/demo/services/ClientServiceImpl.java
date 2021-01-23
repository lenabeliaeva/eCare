package com.example.demo.services;

import com.example.demo.dao.ClientDao;
import com.example.demo.dao.RoleDao;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;
import com.example.demo.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao dao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerNewClient(Client client) throws UserAlreadyExistsException {
        if (dao.findByEmail(client.getEmail()) != null) {
            throw new UserAlreadyExistsException("There is an account with this email: " + client.getEmail());
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
        client.setRoles(roles);
        dao.register(client);
    }

    @Override
    @Transactional
    public Client findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    @Transactional
    public Client findById(long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional
    public Client findByNumber(String number) {
        return dao.findByNumber(number);
    }

    @Override
    @Transactional
    public List<Client> getAll() {
        return dao.getAll();
    }
}
