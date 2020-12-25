package com.example.demo.controllers;

import com.example.demo.models.Tariff;
import com.example.demo.services.TariffService;
import com.example.demo.services.TariffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
