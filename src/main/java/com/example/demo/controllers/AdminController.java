package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Tariff;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    ClientService clientService;
    @Autowired
    TariffService tariffService;
    @Autowired
    ContractService contractService;

    @GetMapping(value = "/admin/searchClient")
    public String searchForClientByNumber(@RequestParam String number, Model model) {
        Client client = clientService.findByNumber(number);
        model.addAttribute("client", client);
        return "redirect:/admin/clientProfile";
    }

    @GetMapping(value = "/admin/clients")
    public String showAllClients(Model model) {
        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        return "/admin/clients";
    }

    @GetMapping(value = "/admin/clientProfile/{clientId}")
    public String showClientProfile(@PathVariable long clientId, Model model) {
        List<Contract> clientContracts = contractService.getClientsContracts(clientId);
        Client client = clientService.findById(clientId);
        model.addAttribute("clientContracts", clientContracts);
        model.addAttribute("client", client);
        return "/admin/clientProfile";
    }
}
