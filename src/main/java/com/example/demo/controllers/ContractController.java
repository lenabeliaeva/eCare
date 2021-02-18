package com.example.demo.controllers;

import com.example.demo.models.Contract;
import com.example.demo.services.ClientService;
import com.example.demo.services.ContractService;
import com.example.demo.services.OptionService;
import com.example.demo.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /**
     * This method is used for getting a new number for a new contract
     *
     * @param clientId
     * @param model
     * @return
     */
    @GetMapping(value = "/admin/signContract/{clientId}")
    public String createContract(@PathVariable long clientId, Model model) {
        Contract contract = new Contract();
        contract.setNumber(contractService.getGeneratedNumber());
        model.addAttribute("newContract", contract);
        model.addAttribute("client", clientService.findById(clientId));
        model.addAttribute("tariffs", tariffService.getAll());
        return "/admin/addNewContract";
    }

    /**
     * This method is used to save new contract with connected tariff and client
     *
     * @param clientId
     * @param tariffId
     * @param contract
     * @return
     */
    @PostMapping(value = "/admin/signContract/{clientId}/{tariffId}")
    public String saveCreatedContract(
            @PathVariable long clientId,
            @PathVariable long tariffId,
            @ModelAttribute("newContract") Contract contract) {
        contractService.saveContract(contract, clientId, tariffId);
        return "redirect:/admin/clientProfile/{clientId}";
    }

    /**
     * This method is used to watch tariffs available to change for existing contract
     *
     * @param contractId
     * @param model
     * @return view with available tariffs
     */
    @GetMapping(value = "/contract/changeTariff/{contractId}")
    public String changeTariff(@PathVariable long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        model.addAttribute("tariffs", tariffService.getNotAddedToContractTariffs(contract.getTariff().getId()));
        model.addAttribute("contract", contract);
        return "/contract/changeTariff";
    }

    /**
     * This method is used to show available for connection options
     * @param contractId
     * @param model
     * @return view with options list
     */
    @GetMapping(value = "/contract/connectOptions/{contractId}")
    public String showOptions(@PathVariable long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        model.addAttribute("availableOptions", optionService.getAllNotAddedToContractOptions(contract));
        model.addAttribute("contract", contract);
        return "/contract/addOptionsToContract";
    }

    @PostMapping(value = "/contract/disconnectOption/{contractId}/{optionId}")
    public String disconnectOption(@PathVariable long contractId, @PathVariable long optionId) {
        contractService.disconnectOption(contractId, optionId);
        return "redirect:/contract/options/{contractId}";
    }

    /**
     * This method is used to show options included in contract and tariff
     * @param contractId
     * @param model
     * @return view with options list
     */
    @GetMapping(value = "/contract/options/{contractId}")
    public String showContractOptions(@PathVariable long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        model.addAttribute("options", optionService.getContractOptions(contract));
        model.addAttribute("contract", contract);
        return "/contract/contractOptions";
    }
}
