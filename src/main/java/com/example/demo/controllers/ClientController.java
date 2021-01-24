package com.example.demo.controllers;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;
import com.example.demo.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@Controller
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    ContractService contractService;
    @Autowired
    SecurityService securityService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerClient(@ModelAttribute("client") @Valid Client client, BindingResult br) {
        if (br.hasErrors()) {
            return "registration";
        }
        try {
            clientService.registerNewClient(client);
            securityService.autologin(client.getEmail(), client.getPassword());
        } catch (UserAlreadyExistsException e) {
            //TODO:add message about it in view
            return "registration";
        }
        return "redirect:/profile";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam String error, @RequestParam String logOut) {
        if (error != null) {
            model.addAttribute("error", "Username and password are invalid");
        }
        if (logOut != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    @GetMapping("/profile")
    public String openProfile(Model model) {
        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
        for (GrantedAuthority authority :
                authorities) {
            role = authority.getAuthority();
        }
        if (role.equals("ROLE_USER")) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            Client client = clientService.findByEmail(userDetails.getUsername());
            model.addAttribute("client", client);
            return "client/profile";
        } else {
            return "login";
        }
    }

    @GetMapping("/")
    public String showWelcomePage() {

        return "welcome";
    }
}
