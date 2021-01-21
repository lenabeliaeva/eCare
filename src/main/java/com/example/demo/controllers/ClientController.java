package com.example.demo.controllers;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ClientController {
    private ClientService clientService = new ClientServiceImpl();
    private ContractService contractService = new ContractServiceImpl();
    private  SecurityService securityService = new SecurityServiceImpl();

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
        }
        catch (UserAlreadyExistsException e) {
            //TODO:add message about it in view
            return "registration";
        }
        return "redirect:/client";
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

    @GetMapping("/")
    public String showWelcomePage() {

        return "welcome";
    }

    @GetMapping("/client")
    public String openProfile(@ModelAttribute("client") @Valid Client client, Model model) {
        List<Contract> contracts = contractService.getClientsContracts(client.getId());
        model.addAttribute("contracts", contracts);
        return "client/profile";
    }
}
