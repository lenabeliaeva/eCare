package com.example.demo.controllers;

import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.OptionService;
import com.example.demo.services.TariffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class TariffController {

    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;

    @GetMapping(value = "/admin/addNewTariff")
    public String addNewTariff(Model model) {
        model.addAttribute("newTariff", new Tariff());
        return "/admin/addNewTariff";
    }

    @PostMapping(value = "/admin/tariffs")
    public String saveTariff(@Valid @ModelAttribute("newTariff") Tariff tariff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin/addNewTariff";
        }
        Tariff saved = tariffService.add(tariff);
        model.addAttribute("tariffId", saved.getId());
        log.info("New tariff is saved to DB");
        return "redirect:/admin/addOption/{tariffId}";
    }

    @GetMapping(value = "/tariffs")
    public @ResponseBody
    List<?> getTariffs() {
        return tariffService.getAll();
    }

    @GetMapping("/admin/tariffs")
    public String showTariffs(Model model) {
        model.addAttribute("tariffs", tariffService.getAll());
        return "admin/tariffs";
    }

    @PostMapping(value = "/admin/tariffs/delete/{tariffId}")
    public String deleteTariff(@PathVariable long tariffId) {
        if (tariffService.delete(tariffService.getById(tariffId))) {
            log.info("Tariff is deleted from DB");
        } else {
            log.info("Tariff couldn't be deleted as it is contained in contract(s)");
        }
        return "redirect:/admin/tariffs";
    }

    @GetMapping(value = "/admin/tariffs/edit/{tariffId}")
    public String editTariff(@PathVariable long tariffId, Model model) {
        Tariff tariff = tariffService.getById(tariffId);
        model.addAttribute("editedTariff", tariff);
        return "/admin/editTariff";
    }

    @PostMapping(value = "/admin/tariffs/edit")
    public String saveEditedTariff(@ModelAttribute("editedTariff") Tariff edited) {
        tariffService.edit(edited);
        log.info("Tariff is updated in DB");
        return "redirect:/admin/tariffs";
    }

    @GetMapping(value = "/admin/tariffs/options/{tariffId}")
    public String showOptionsForTariffToAdd(@PathVariable long tariffId, Model model) {
        Tariff tariff = tariffService.getById(tariffId);
        model.addAttribute("tariff", tariff);
        model.addAttribute("options", optionService.getAllNotAddedToTariff(tariff));
        model.addAttribute("selectedOptions", tariff.getOptions());
        return "/admin/chooseOptions";
    }

    @PostMapping(value = "/admin/tariffs/options/{tariffId}/{optionId}")
    public String addOption(@PathVariable long optionId, @PathVariable long tariffId) {
        Tariff tariff = tariffService.getById(tariffId);
        Option option = optionService.getById(optionId);
        tariffService.addOption(tariff, option);
        log.info("New option is added to tariff");
        return "redirect:/admin/tariffs/options/{tariffId}";
    }

    @PostMapping(value = "/deleteOption/{tariffId}/{optionId}")
    public String deleteAddedOption(@PathVariable long tariffId, @PathVariable long optionId) {
        Tariff tariff = tariffService.getById(tariffId);
        Option option = optionService.getById(optionId);
        if (!tariffService.deleteOption(tariff, option)) {
            return "redirect:/admin/showOptions/{tariffId}";
        }
        log.info("Option is deleted from tariff");
        return "redirect:/admin/tariffs";
    }
}
