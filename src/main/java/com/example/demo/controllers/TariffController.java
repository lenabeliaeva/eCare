package com.example.demo.controllers;

import com.example.demo.models.Tariff;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TariffController {

    @RequestMapping(value = "/addNewTariff", method = RequestMethod.GET)
    public ModelAndView addNewTariff() {
        return new ModelAndView("addNewTariff", "command", new Tariff());
    }

    @RequestMapping(value = "/saveTariff", method = RequestMethod.POST)
    public String saveTariff(@ModelAttribute("addNewTariff") Tariff tariff, ModelMap modelMap) {
        modelMap.addAttribute("id", tariff.getId());
        modelMap.addAttribute("name", tariff.getName());
        modelMap.addAttribute("price", tariff.getPrice());
        return "tariffs";
    }
}
