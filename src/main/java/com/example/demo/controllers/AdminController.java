package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Tariff;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
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
        List<Client> clients = clientService.findByNumber(number);
        model.addAttribute("clients", clients);
        return "/admin/clients";
    }

    @GetMapping(value = "/admin/clients")
    public String showAllClients(Model model) {
        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        return "/admin/clients";
    }

    @GetMapping(value = "/admin/clientProfile/{clientId}")
    public String showClientProfile(@PathVariable long clientId, Model model) {
        Client client = clientService.findById(clientId);
        List<Contract> clientContracts = contractService.getClientsContracts(clientId);
        model.addAttribute("client", client);
        model.addAttribute("clientContracts", clientContracts);
        return "/admin/clientProfile";
    }

    @PostMapping("/admin/blockContract/{clientId}/{contractId}")
    public String blockContract(@PathVariable long contractId) {
        contractService.blockByAdmin(contractId);
        return "redirect:/admin/clientProfile/{clientId}";
    }

    @PostMapping("/admin/unblockContract/{clientId}/{contractId}")
    public String unblockContract(@PathVariable long contractId) {
        contractService.unblockByAdmin(contractId);
        return "redirect:/admin/clientProfile/{clientId}";
    }
}
