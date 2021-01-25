package com.example.demo.controllers;

import com.example.demo.models.Contract;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.ClientService;
import com.example.demo.services.ContractService;
import com.example.demo.services.OptionService;
import com.example.demo.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping(value = "/admin/signContract/{clientId}")
    public String createContract(@PathVariable long clientId, Model model) {
        String number = contractService.getGeneratedNumber();
        Contract contract = new Contract();
        contract.setNumber(number);
        contract.setClient(clientService.findById(clientId));
        Contract newContract = contractService.saveContract(contract);
        model.addAttribute("newContract", newContract);
        model.addAttribute("tariffs", tariffService.getAll());
        return "/admin/addNewContract";
    }

    @PostMapping(value = "/admin/connectTariff/{tariffId}/{contractId}")
    public String connectTariff(@PathVariable long tariffId, @PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        contract.setTariff(tariffService.getById(tariffId));
        contractService.updateContract(contract);
        return "redirect:/admin/showOptions/{tariffId}/{contractId}";
    }

    @GetMapping(value = "/admin/showOptions/{tariffId}/{contractId}")
    public String showOptions(@PathVariable long tariffId, @PathVariable long contractId, Model model) {
        model.addAttribute("connectedOptions", optionService.getAllForCertainTariff(tariffId));
        model.addAttribute("availableOptions", optionService.getAllNotAddedToTariff(tariffId));
        model.addAttribute("contract", contractService.getContractById(contractId));
        return "/admin/addOptionsToContract";
    }

    @PostMapping(value = "/admin/connectOption/{optionId}/{contractId}")
    public String connectOptions(@PathVariable long optionId, @PathVariable long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        Option option = optionService.getById(optionId);
        contractService.connectOption(contract, option);
        model.addAttribute("tariffId", contract.getTariff().getId());
        return "redirect:/admin/showOptions/{tariffId}/{contractId}";
    }

    @PostMapping(value = "/admin/clientProfile/{contractId}/{tariffId}")
    public String changeClientTariff(@PathVariable long contractId, @PathVariable long tariffId) {
        Contract contract = contractService.getContractById(contractId);
        Tariff tariff = tariffService.getById(tariffId);
        contractService.changeTariff(contract, tariff);
        return "/admin/clientProfile";
    }
}
