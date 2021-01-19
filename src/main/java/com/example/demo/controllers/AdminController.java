package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.services.ClientService;
import com.example.demo.services.ClientServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private ClientService service = new ClientServiceImpl();

    @GetMapping(value = "/admin")
    public String showAdminPage() {
        return "admin";
    }

    @GetMapping(value = "/admin/searchClient")
    public String searchForClientByNumber(@RequestParam String number, Model model) {
        Client client = service.findByNumber(number);
        model.addAttribute("client", client);
        return "/admin/clientProfile";
    }

    @PostMapping(value = "/admin/clientProfile")
    public String showClientProfile(@ModelAttribute("client") Client client) {
        return "clientProfile";
    }
}
