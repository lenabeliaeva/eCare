package com.example.demo.controllers;

import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("clients", clientService.findByNumber(number));
        return "/admin/clients";
    }

    @GetMapping(value = "/admin/clients")
    public String showAllClients(Model model) {
        model.addAttribute("clients", clientService.getAll());
        return "/admin/clients";
    }

    @GetMapping(value = "/admin/clientProfile/{clientId}")
    public String showClientProfile(@PathVariable long clientId, Model model) {
        model.addAttribute("client", clientService.findById(clientId));
        model.addAttribute("clientContracts", contractService.getClientsContracts(clientId));
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
