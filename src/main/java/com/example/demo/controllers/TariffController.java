package com.example.demo.controllers;

import com.example.demo.models.Tariff;
import com.example.demo.services.TariffService;
import com.example.demo.services.TariffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TariffController {

    private final TariffService tariffService = new TariffServiceImpl();

    @RequestMapping(value = "/addNewTariff", method = RequestMethod.POST)
    public String addNewTariff(Model model) {
        model.addAttribute("newTariff", new Tariff());
        return "addNewTariff";
    }

    @RequestMapping(value = "/saveTariff", method = RequestMethod.POST)
    public String showTariffs(@ModelAttribute("newTariff") Tariff tariff, Model model) {
        tariffService.add(tariff);
        List<Tariff> tariffs = tariffService.getAll();
        model.addAttribute("tariffs", tariffs);
        return "tariffs";
    }
}
