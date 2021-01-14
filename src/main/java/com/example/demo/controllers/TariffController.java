package com.example.demo.controllers;

import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.OptionService;
import com.example.demo.services.OptionServiceImpl;
import com.example.demo.services.TariffService;
import com.example.demo.services.TariffServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
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
    public String saveTariff(@Valid @ModelAttribute("newTariff") Tariff tariff, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addNewTariff";
        }
        tariffService.add(tariff);
        log.info("New tariff is saved to DB");
        Tariff saved = tariffService.getLastAddedTariff();
        long id = saved.getId();
        redirectAttributes.addAttribute("id", id);
        return "redirect:/addOption/{id}";
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
        log.info("Tariff is deleted from DB");
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
        log.info("Tariff is updated in DB");
        return "redirect:/tariffs";
    }

    @RequestMapping(value = "/addOption/{tariffId}")
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
        log.info("New option is added to tariff");
        return "redirect:/showOptions/{tariffId}";
    }

    @PostMapping(value = "/deleteOption/{tariffId}/{optionId}")
    public String deleteAddedOption(@PathVariable String tariffId, @PathVariable String optionId) {
        Tariff tariff = tariffService.getById(Long.parseLong(tariffId));
        Option option = optionService.getById(Long.parseLong(optionId));
        tariffService.deleteOption(tariff, option);
        log.info("Option is deleted from tariff");
        return "redirect:/tariffs";
    }
}
