package com.example.demo.controllers;

import com.example.demo.models.Option;
import com.example.demo.services.OptionService;
import com.example.demo.services.OptionServiceImpl;
import com.example.demo.services.TariffService;
import com.example.demo.services.TariffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class OptionController {

    private final OptionService optionService = new OptionServiceImpl();
    private final TariffService tariffService = new TariffServiceImpl();

    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public String showAllOptions(Model model) {
        model.addAttribute("options", optionService.getAll());
        return "options";
    }

    @RequestMapping(value = "/showOptions/{tariffId}", method = RequestMethod.GET)
    public String showOptionsForTariff(@PathVariable String tariffId, Model model) {
        List<Option> options = optionService.getAllForCertainTariff(Long.parseLong(tariffId));
        model.addAttribute("tariffOptions", options);
        model.addAttribute("tariffId", Long.parseLong(tariffId));
        return "tariffOptions";
    }

    @RequestMapping(value = "/createOption", method = RequestMethod.POST)
    public String createOption(Model model) {
        model.addAttribute("newOption", new Option());
        return "addNewOption";
    }

    @RequestMapping(value = "/saveOption", method = RequestMethod.POST)
    public String saveOption(@ModelAttribute("newOption") Option option) {
        optionService.add(option);
        return "redirect:/options";
    }

    @RequestMapping(value = "/editOption/{optionId}", method = RequestMethod.POST)
    public String editOption(@PathVariable String optionId, Model model) {
        model.addAttribute("editedOption", optionService.getById(Long.parseLong(optionId)));
        return "editOption";
    }

    @RequestMapping(value = "/saveEditedOption", method = RequestMethod.POST)
    public String saveEditedOption(@ModelAttribute("editedOption") Option option) {
        optionService.edit(option);
        return "redirect:/options";
    }

    @RequestMapping("/deleteOption/{optionId}")
    public String deleteOption(@PathVariable String optionId) {
        optionService.delete(optionService.getById(Long.parseLong(optionId)));
        return "redirect:/options";
    }
}
