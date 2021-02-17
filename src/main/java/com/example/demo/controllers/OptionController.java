package com.example.demo.controllers;

import com.example.demo.dto.OptionDto;
import com.example.demo.models.Option;
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
public class OptionController {

    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;

    @GetMapping("/options")
    public @ResponseBody List<OptionDto> getAllOptions() {
        return optionService.getAll();
    }

    @GetMapping(value = "/admin/options")
    public String showAllOptions(Model model) {
        model.addAttribute("options", optionService.getAll());
        return "admin/options";
    }

    @GetMapping(value = "/admin/showOptions/{tariffId}")
    public String showOptionsForTariff(@PathVariable long tariffId, Model model) {
        List<?> options = optionService.getAllForCertainTariff(tariffId);
        model.addAttribute("options", options);
        model.addAttribute("tariff", tariffService.getById(tariffId));
        return "/admin/tariffOptions";
    }

    @GetMapping(value = "/profile/showOptions/{tariffId}")
    public String showOptionsForTariffForClient(@PathVariable long tariffId, Model model) {
        List<?> options = optionService.getAllForCertainTariff(tariffId);
        model.addAttribute("options", options);
        model.addAttribute("tariff", tariffService.getById(tariffId));
        return "client/tariffOptions";
    }

    @PostMapping(value = "/createOption")
    public String createOption(Model model) {
        model.addAttribute("newOption", new Option());
        return "/admin/addNewOption";
    }

    @PostMapping(value = "/saveOption")
    public String saveOption(@Valid @ModelAttribute("newOption") Option option, BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/addNewOption";
        }
        optionService.add(option);
        log.info("New option is saved to DB");
        return "redirect:/admin/options";
    }

    @GetMapping(value = "/editOption/{optionId}")
    public String editOption(@PathVariable long optionId, Model model) {
        model.addAttribute("editedOption", optionService.getById(optionId));
        return "/admin/editOption";
    }

    @PostMapping(value = "/editOption")
    public String saveEditedOption(@ModelAttribute Option edited) {
        optionService.edit(edited);
        return "redirect:/admin/options";
    }

    @PostMapping("/deleteOption/{optionId}")
    public String deleteOption(@PathVariable long optionId) {
        optionService.delete(optionId);
        return "redirect:/admin/options";
    }

    @GetMapping("/admin/options/incompatible/{optionId}")
    public String showIncompatibleOptions(@PathVariable long optionId, Model model) {
        model.addAttribute("first", optionService.getById(optionId));
        model.addAttribute("incompatible", optionService.getIncompatibleOptions(optionId));
        model.addAttribute("compatible", optionService.getCompatible(optionId));
        return "admin/incompatibles";
    }

    @PostMapping("/admin/options/incompatible/{firstId}/{secondId}")
    public String addIncompatibleOption(@PathVariable long firstId, @PathVariable long secondId) {
        optionService.addIncompatibleOption(firstId, secondId);
        return "redirect:/admin/options/incompatible/{firstId}";
    }

    @PostMapping("/admin/options/deleteIncompatible/{firstId}/{secondId}")
    public String deleteIncompatibleOption(@PathVariable long firstId, @PathVariable long secondId) {
        optionService.deleteIncompatibleOption(firstId, secondId);
        return "redirect:/admin/options/incompatible/{firstId}";
    }
}
