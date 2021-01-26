package com.example.demo.controllers;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.models.Client;
import com.example.demo.models.Contract;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@Controller
@SessionAttributes("role")
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
    public String registerClient(@ModelAttribute("client") @Valid Client client, BindingResult br, HttpSession httpSession) {
        if (br.hasErrors()) {
            return "registration";
        }
        try {
            clientService.registerNewClient(client);
            securityService.autologin(client.getEmail(), client.getPassword());
            httpSession.setAttribute("role", clientService.getRole());
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
    public String openProfile(Model model, HttpSession httpSession) {
        Client client = clientService.getAuthorizedClient();
        if (client == null) {
            return "login";
        }
        httpSession.setAttribute("role", clientService.getRole());
        model.addAttribute("client", client);
        model.addAttribute("contracts", contractService.getClientsContracts(client.getId()));
        return "client/profile";
    }

//    TODO
    @GetMapping("/")
    public String showWelcomePage() {

        return "welcome";
    }

    @GetMapping("/profile/blockContract/{contractId}")
    public String blockContractByClient(@PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        contractService.blockByClient(contract);
        return "redirect:/profile";
    }

    @GetMapping("/profile/unblockContract/{contractId}")
    public String unblockContractByClient(@PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        contractService.unblockByClient(contract);
        return "redirect:/profile";
    }
}
