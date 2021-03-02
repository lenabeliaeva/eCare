package com.example.demo.services.impl;

import com.example.demo.dao.api.ClientDao;
import com.example.demo.dao.api.RoleDao;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.entities.Client;
import com.example.demo.models.entities.Role;
import com.example.demo.services.api.ClientService;
import lombok.extern.log4j.Log4j;
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
@Log4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao dao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Before saving new client it is necessary to check its email and passport as they should be unique.
     * If they are unique password is encoded, role is added and new client is saved.
     * As client has PasswordMatches validation we have to set password to password confirm.
     *
     * @param client
     * @throws UserAlreadyExistsException
     */
    @Override
    @Transactional
    public void registerNewClient(Client client) throws UserAlreadyExistsException {
        if (dao.findByEmail(client.getEmail()) != null) {
            log.info("Client can't be saved because there is an account with this email: " + client.getEmail());
            throw new UserAlreadyExistsException("There is an account with this email: " + client.getEmail());
        }
        if (dao.findByPassport(client.getPassport()) != null) {
            log.info("Client can't be saved because there is an account with this passport: " + client.getPassport());
            throw new UserAlreadyExistsException("There is an account with this passport: " + client.getPassport());
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setPasswordConfirm(client.getPassword());
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
        client.setRoles(roles);
        dao.register(client);
        log.info("Client is saved to DB");
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

    /**
     * When client's data are edited roles are lost.
     * That's why we have to fetch them from db and save with edited client.
     * @param client
     */
    @Override
    @Transactional
    public void editClientProfile(Client client) {
        String authName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Client oldClient = findByEmail(authName);
        client.setRoles(oldClient.getRoles());
        dao.update(client);
        log.info("Client info is updated");
    }

    /**
     * This method is used to show client's profile after authorization.
     * As Client has PasswordMatches validation we have to set password confirm to password
     * because this field is not hold in DB.
     *
     * @return authorized client
     */
    @Override
    @Transactional
    public Client getAuthorizedClient() {
        String role = getRole();
        if (role != null && role.equals("ROLE_USER")) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            Client client = dao.findByEmail(userDetails.getUsername());
            client.setPasswordConfirm(client.getPassword());
            return client;
        } else {
            return null;
        }
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
