package com.example.demo.controllers;

import com.example.demo.dto.OptionDto;
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

    @GetMapping(value = "/admin/options/{tariffId}")
    public String showOptionsForTariff(@PathVariable long tariffId, Model model) {
        Tariff tariff = tariffService.getById(tariffId);
        model.addAttribute("options", tariff.getOptions());
        model.addAttribute("tariff", tariff);
        return "/admin/tariffOptions";
    }

    @GetMapping(value = "/admin/createOption")
    public String createOption(Model model) {
        model.addAttribute("newOption", new Option());
        return "/admin/addNewOption";
    }

    @PostMapping(value = "/options")
    public String saveOption(@Valid @ModelAttribute("newOption") Option option, BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/addNewOption";
        }
        optionService.add(option);
        log.info("New option is saved to DB");
        return "redirect:/admin/options";
    }

    @GetMapping(value = "/admin/editOption/{optionId}")
    public String editOption(@PathVariable long optionId, Model model) {
        model.addAttribute("editedOption", optionService.getById(optionId));
        return "/admin/editOption";
    }

    @PostMapping(value = "/editOption")
    public String saveEditedOption(@ModelAttribute Option edited) {
        optionService.edit(edited);
        return "redirect:/admin/options";
    }

    @PostMapping("/admin/deleteOption/{optionId}")
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

    @GetMapping("/admin/options/dependent/{optionId}")
    public String showDependentOptions(@PathVariable long optionId, Model model) {
        model.addAttribute("first", optionService.getById(optionId));
        model.addAttribute("dependent", optionService.getDependentOptions(optionId));
        model.addAttribute("independent", optionService.getIndependentOptions(optionId));
        return "admin/dependentOptions";
    }

    @PostMapping("/admin/options/dependent/{firstId}/{secondId}")
    public String addDependentOption(@PathVariable long firstId, @PathVariable long secondId) {
        optionService.addDependentOption(firstId, secondId);
        return "redirect:/admin/options/dependent/{firstId}";
    }

    @PostMapping("/admin/options/deleteDependent/{firstId}/{secondId}")
    public String deleteDependentOption(@PathVariable long firstId, @PathVariable long secondId) {
        optionService.deleteDependentOption(firstId, secondId);
        return "redirect:/admin/options/dependent/{firstId}";
    }
}
