package com.example.demo.services;

import com.example.demo.dao.ClientDao;
import com.example.demo.models.Client;
import com.example.demo.models.Role;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Log4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDao dao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = dao.findByEmail(email);
        if (client == null) {
            log.info("Client with email " + email + " is not found");
            throw new UsernameNotFoundException("Client is not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role :
                client.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        log.info("Client with email " + email + " is loaded");
        return new User(client.getEmail(), client.getPassword(), grantedAuthorities);
    }
}
