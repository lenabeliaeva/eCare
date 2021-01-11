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

    @PostMapping(value = "/addNewTariff")
    public String addNewTariff(Model model) {
        model.addAttribute("newTariff", new Tariff());
        return "addNewTariff";
    }

    @PostMapping(value = "/saveTariff")
    public String saveTariff(@ModelAttribute("newTariff") Tariff tariff) {
        tariffService.add(tariff);
        return "redirect:/tariffs";
    }

    @GetMapping(value = "/tariffs")
    public String showTariffs(Model model) {
        List<?> tariffs = tariffService.getAll();
        model.addAttribute("tariffs", tariffs);
        return "tariffs";
    }

    @PostMapping(value = "/deleteTariff/{tariffId}")
    public String deleteTariff(@PathVariable String tariffId) {
        tariffService.delete(tariffService.getById(Long.parseLong(tariffId)));
        return "redirect:/tariffs";
    }

    @PostMapping(value = "/editTariff/{tariffId}")
    public String editTariff(@PathVariable String tariffId, Model model) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        model.addAttribute("editedTariff", tariff);
        return "editTariff";
    }

    @PostMapping(value = "/saveEditedTariff")
    public String saveEditedTariff(@ModelAttribute("editedTariff") Tariff edited) {
        Tariff initial = tariffService.getById(edited.getId());
        if (edited.getOptions() == null)
            edited.setOptions(initial.getOptions());
        tariffService.edit(edited);
        return "redirect:/tariffs";
    }

    @PostMapping(value = "/addOption/{tariffId}")
    public String addOptionForTariff(@PathVariable String tariffId, Model model) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        model.addAttribute("addOptionTariff", tariff);
        model.addAttribute("options", optionService.getAllNotAddedToTariff(Long.parseLong(tariffId)));
        return "chooseOptions";
    }

    @PostMapping(value = "/addOption/{tariffId}/{optionId}")
    public String saveAddedOption(@PathVariable String optionId, @PathVariable String tariffId) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        Option option = optionService.getById(Long.parseLong(optionId));
        tariffService.addOption(tariff, option);
        return "redirect:/showOptions/{tariffId}";
    }

    @PostMapping(value = "/deleteOption/{tariffId}/{optionId}")
    public String deleteAddedOption(@PathVariable String tariffId, @PathVariable String optionId) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        Option option = optionService.getById(Long.parseLong(optionId));
        tariffService.deleteOption(tariff, option);
        return "redirect:/tariffs";
    }
}
