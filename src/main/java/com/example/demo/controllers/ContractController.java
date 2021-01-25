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
        Contract contract = new Contract();
        contract.setNumber(contractService.getGeneratedNumber());
        contract.setClient(clientService.findById(clientId));
        Contract newContract = contractService.saveContract(contract);
        model.addAttribute("newContract", newContract);
        model.addAttribute("tariffs", tariffService.getAll());
        return "/admin/addNewContract";
    }

    @PostMapping(value = "/admin/connectTariff/{tariffId}/{contractId}")
    public String connectTariff(@PathVariable long tariffId, @PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        Tariff tariff = tariffService.getById(tariffId);
        contractService.connectTariff(contract, tariff);
        return "redirect:/admin/showOptions/{tariffId}/{contractId}";
    }

    @GetMapping(value = "/admin/showOptions/{tariffId}/{contractId}")
    public String showOptions(@PathVariable long tariffId, @PathVariable long contractId, Model model) {
//        model.addAttribute("tariffOptions", optionService.getAllForCertainTariff(tariffId));
        model.addAttribute("connectedOptions", optionService.getAllForCertainContract(contractId, tariffId));
        model.addAttribute("availableOptions", optionService.getAllNotAddedToContract(contractId, tariffId));
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

    @PostMapping(value = "/admin/disconnectOption/{contractId}/{optionId}")
    public String disconnectOptions(@PathVariable long contractId, @PathVariable long optionId) {
        Contract contract = contractService.getContractById(contractId);
        Option option = optionService.getById(optionId);
        contractService.disconnectOption(contract, option);
        return "redirect:/admin/showContractOptions/{contractId}";
    }

    @PostMapping(value = "/admin/clientProfile/{contractId}")
    public String changeClientTariff(@PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        contractService.changeTariff(contract, tariff);
        return "/admin/clientProfile";
    }

    @PostMapping(value = "/profile/{contractId}")
    public String changeTariffByClient(@PathVariable long contractId) {
        Contract contract = contractService.getContractById(contractId);
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        contractService.changeTariff(contract, tariff);
        return "/client/profile";
    }

//    TODO:add contract to model
    @GetMapping(value = "/admin/showContractOptions/{contractId}")
    public String showContractOptions(@PathVariable long contractId, Model model) {
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        model.addAttribute("options", optionService.getAllForCertainContract(contractId, tariff.getId()));
        return "/admin/contractOptions";
    }

    @GetMapping(value = "/profile/contractOptions/{contractId}")
    public String showContractOptionsForClient(@PathVariable long contractId, Model model) {
        Tariff tariff = contractService.getContractById(contractId).getTariff();
        model.addAttribute("options", optionService.getAllForCertainContract(contractId, tariff.getId()));
        return "/client/contractOptions";
    }


}
