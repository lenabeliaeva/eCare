package com.example.demo.services;

import com.example.demo.dao.ClientDao;
import com.example.demo.models.Client;
import com.example.demo.models.Role;
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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ClientDao dao;

    @Autowired
    public void setDao(ClientDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = dao.findByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException("Client is not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role:
                client.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(client.getEmail(), client.getPassword(), grantedAuthorities);
    }
}
