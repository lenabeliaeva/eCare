package com.example.demo.controllers;

import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.OptionService;
import com.example.demo.services.OptionServiceImpl;
import com.example.demo.services.TariffService;
import com.example.demo.services.TariffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TariffController {

    private final TariffService tariffService = new TariffServiceImpl();
    private final OptionService optionService = new OptionServiceImpl();

    @RequestMapping(value = "/addNewTariff", method = RequestMethod.POST)
    public String addNewTariff(Model model) {
        model.addAttribute("newTariff", new Tariff());
        return "addNewTariff";
    }

    @RequestMapping(value = "/saveTariff", method = RequestMethod.POST)
    public String saveTariff(@ModelAttribute("newTariff") Tariff tariff) {
        tariffService.add(tariff);
        return "redirect:/tariffs";
    }

    @RequestMapping(value = "/tariffs", method = RequestMethod.GET)
    public String showTariffs(Model model) {
        List<Tariff> tariffs = tariffService.getAll();
        model.addAttribute("tariffs", tariffs);
        return "tariffs";
    }

    @RequestMapping(value = "/deleteTariff/{tariffId}", method = RequestMethod.POST)
    public String deleteTariff(@PathVariable String tariffId) {
        tariffService.delete(tariffService.getById(Long.parseLong(tariffId)));
        return "redirect:/tariffs";
    }

    @RequestMapping(value = "/editTariff/{tariffId}", method = RequestMethod.POST)
    public String editTariff(@PathVariable String tariffId, Model model) {
        model.addAttribute("editedTariff", tariffService.getById(Long.parseLong(tariffId)));
        model.addAttribute("options", optionService.getAllForCertainTariff(Long.parseLong(tariffId)));
        return "editTariff";
    }

    @RequestMapping(value = "/saveEditedTariff", method = RequestMethod.POST)
    public String saveEditedTariff(@ModelAttribute("editedTariff") Tariff tariff) {
        tariffService.edit(tariff);
        return "redirect:/tariffs";
    }

    @RequestMapping(value = "/addOption/{tariffId}", method = RequestMethod.POST)
    public String addOptionForTariff(@PathVariable String tariffId, Model model) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        model.addAttribute("addOptionTariff", tariff);
        model.addAttribute("options", optionService.getAll());
        return "chooseOptions";
    }

    @RequestMapping(value = "/addOption/{tariffId}/{optionId}", method = RequestMethod.POST)
    public String saveAddedOption(@PathVariable String optionId, @PathVariable String tariffId) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        Option option = optionService.getById(Long.parseLong(optionId));
        tariffService.addOption(tariff, option);
        return "redirect:/addOption/{tariffId}";
    }

    @RequestMapping(value = "/deleteOption/{tariffId}/{optionId}", method = RequestMethod.POST)
    public String deleteAddedOption(@PathVariable String tariffId, @PathVariable String optionId) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        Option option = optionService.getById(Long.parseLong(optionId));
        tariffService.deleteOption(tariff, option);
        return "redirect:/editTariff/{tariffId}";
    }
}
