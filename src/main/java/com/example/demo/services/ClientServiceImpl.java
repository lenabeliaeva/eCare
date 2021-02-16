package com.example.demo.services;

import com.example.demo.dao.ClientDao;
import com.example.demo.dao.RoleDao;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;
import com.example.demo.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
        if (dao.findByPassport(client.getPassport()) != null) {
            throw new UserAlreadyExistsException("There is an account with this passport: " + client.getPassport());
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
    public List<Client> findByNumber(String number) {
        return dao.findByNumber(number);
    }

    @Override
    @Transactional
    public List<Client> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public Client getAuthorizedClient() {
        String role = getRole();
        if (role != null) {
            if (role.equals("ROLE_USER")) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
                return findByEmail(userDetails.getUsername());
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Client getAuthorizedAdmin() {
        String role = getRole();
        if (role != null) {
            if (role.equals("ROLE_ADMIN")) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
                return findByEmail(userDetails.getUsername());
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void editClientProfile(Client client) {
        dao.update(client);
    }

    private String getRole() {
        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
        for (GrantedAuthority authority :
                authorities) {
            role = authority.getAuthority();
        }
        return role;
    }
}
