package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.ClientService;
import com.example.demo.services.ContractService;
import com.example.demo.services.OptionService;
import com.example.demo.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContractController {
    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;
    @Autowired
    ClientService clientService;
    @Autowired
    ContractService contractService;

//    FIXME
    @GetMapping(value = "/admin/signContract/{clientId}")
    public String createContract(@PathVariable long clientId, Model model) {
        Contract contract = new Contract();
        contract.setNumber(contractService.getGeneratedNumber());
        contract.setClient(clientService.findById(clientId));
        Contract newContract = contractService.saveContract(contract);
        model.addAttribute("newContract", newContract);
        model.addAttribute("tariffs", tariffService.getAll());
        return "/admin/addNewContract";
    }

    @PostMapping(value = "/admin/connectTariff/{contractId}")
    public String changeTariff(@PathVariable long contractId, Model model) {
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        model.addAttribute("tariffs", tariffService.getNotAddedToContractTariffs(tariff));
        model.addAttribute("contract", contractService.getContractById(contractId));
        return "/admin/changeTariff";
    }

    @PostMapping(value = "/admin/connectTariff/{contractId}/{tariffId}")
    public String connectTariff(@PathVariable long tariffId, @PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        Tariff tariff = tariffService.getById(tariffId);
        contractService.connectTariff(contract, tariff);
        return "redirect:/admin/showContractOptions/{contractId}";
    }

    @PostMapping(value = "/admin/connectOptions/{contractId}")
    public String changeOptions(@PathVariable long contractId, Model model) {
        long tariffId = contractService.getContractById(contractId).getTariff().getId();
        model.addAttribute("connectedOptions", optionService.getAllForCertainContract(contractId, tariffId));
        model.addAttribute("availableOptions", optionService.getAllNotAddedToContract(contractId, tariffId));
        model.addAttribute("contract", contractService.getContractById(contractId));
        return "/admin/addOptionsToContract";
    }

    @PostMapping(value = "/admin/connectOption/{contractId}/{optionId}")
    public String connectOptions(@PathVariable long contractId, @PathVariable long optionId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        Option option = optionService.getById(optionId);
        contractService.connectOption(contract, option);
        return "redirect:/admin/showContractOptions/{contractId}";
    }

    @PostMapping(value = "/admin/disconnectOption/{contractId}/{optionId}")
    public String disconnectOptions(@PathVariable long contractId, @PathVariable long optionId) {
        Contract contract = contractService.getContractById(contractId);
        Option option = optionService.getById(optionId);
        contractService.disconnectOption(contract, option);
        return "redirect:/admin/showContractOptions/{contractId}";
    }

    @GetMapping(value = "/admin/showContractOptions/{contractId}")
    public String showContractOptions(@PathVariable long contractId, Model model) {
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        model.addAttribute("options", optionService.getAllForCertainContract(contractId, tariff.getId()));
        model.addAttribute("contract", contractService.getContractById(contractId));
        return "/admin/contractOptions";
    }

    @GetMapping(value = "/profile/contractOptions/{contractId}")
    public String showContractOptionsForClient(@PathVariable long contractId, Model model) {
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        model.addAttribute("options", optionService.getAllForCertainContract(contractId, tariff.getId()));
        model.addAttribute("contract", contractService.getContractById(contractId));
        return "/client/contractOptions";
    }
}
