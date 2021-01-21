package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Tariff;
import com.example.demo.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private ClientService clientService = new ClientServiceImpl();
    private TariffService tariffService = new TariffServiceImpl();
    private ContractService contractService = new ContractServiceImpl();

    @GetMapping(value = "/admin")
    public String showAdminPage() {
        return "admin";
    }

    @GetMapping(value = "/admin/searchClient")
    public String searchForClientByNumber(@RequestParam String number, Model model) {
        Client client = clientService.findByNumber(number);
        model.addAttribute("client", client);
        return "redirect:/admin/clientProfile";
    }

    @PostMapping(value = "/admin/clientProfile")
    public String showClientProfile(@ModelAttribute("client") Client client, Model model) {
        List<Contract> clientContracts = contractService.getClientsContracts(client.getId());
        model.addAttribute("clientContracts", clientContracts);
        return "/admin/clientProfile";
    }

    @GetMapping(value = "/admin/clients")
    public String showAllClients(Model model) {
        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @PostMapping(value = "/admin/clientProfile/{contractId}/{tariffId}")
    public String changeClientTariff(@PathVariable long contractId, @PathVariable long tariffId) {
        Contract contract = contractService.getContractById(contractId);
        Tariff tariff = tariffService.getById(tariffId);
        contractService.changeTariff(contract, tariff);
        return "/admin/clientProfile";
    }
}
