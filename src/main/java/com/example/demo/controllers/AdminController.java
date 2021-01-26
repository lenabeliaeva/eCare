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

    @GetMapping("/admin/blockContract/{contractId}")
    public String blockContractByClient(@PathVariable long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        contractService.blockByAdmin(contract);
        long clientId = contract.getClient().getId();
        model.addAttribute("clientId", clientId);
        return "redirect:/admin/clientProfile/{clientId}";
    }

    @GetMapping("/admin/unblockContract/{contractId}")
    public String unblockContractByClient(@PathVariable long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        contractService.unblockByAdmin(contract);
        long clientId = contract.getClient().getId();
        model.addAttribute("clientId", clientId);
        return "redirect:/admin/clientProfile/{clientId}";
    }
}
