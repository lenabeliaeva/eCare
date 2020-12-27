package com.example.demo.controllers;

import com.example.demo.models.Option;
import com.example.demo.services.OptionService;
import com.example.demo.services.OptionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class OptionController {

    private final OptionService service = new OptionServiceImpl();

    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public String showAllOptions(Model model) {
        model.addAttribute("options", service.getAll());
        return "options";
    }

    @RequestMapping(value = "/showOptions/{tariffId}", method = RequestMethod.POST)
    public String showOptionsForTariff(@PathVariable String tariffId, Model model) {
        List<Option> options = service.getAllForCertainTariff(Long.parseLong(tariffId));
        model.addAttribute("options", options);
        //TODO:this should be another view
        return "options";
    }

    @RequestMapping(value = "/createOption", method = RequestMethod.POST)
    public String createOption(Model model) {
        model.addAttribute("newOption", new Option());
        return "addNewOption";
    }

    @RequestMapping(value = "/saveOption", method = RequestMethod.POST)
    public String saveOption(@ModelAttribute("newOption") Option option) {
        service.add(option);
        return "redirect:/options";
    }

    @RequestMapping(value = "/editOption/{optionId}", method = RequestMethod.POST)
    public String editOption(@PathVariable String optionId) {
        service.edit(service.getById(Long.parseLong(optionId)));
        return "editOption";
    }

    @RequestMapping("/deleteOption/{optionId}")
    public String deleteOption(@PathVariable String optionId) {
        service.delete(service.getById(Long.parseLong(optionId)));
        return "redirect:/options";
    }
}
