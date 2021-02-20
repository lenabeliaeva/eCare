package com.example.demo.controllers;

import com.example.demo.dto.TariffDto;
import com.example.demo.models.Option;
import com.example.demo.models.Tariff;
import com.example.demo.services.OptionService;
import com.example.demo.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class TariffController {

    private static final String TARIFF = "tariff";

    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;

    @GetMapping(value = "/admin/tariffs/new")
    public String addNewTariff(Model model) {
        model.addAttribute("newTariff", new Tariff());
        return "/admin/addNewTariff";
    }

    @PostMapping("/admin/tariffs/new")
    public String saveNewTariffToSession(@Valid @ModelAttribute("newTariff") Tariff tariff, BindingResult result,
                                         HttpSession session) {
        if (result.hasErrors()) {
            return "/admin/addNewTariff";
        }
        session.setAttribute(TARIFF, tariff);
        return "redirect:/admin/tariffs/options";
    }

    @PostMapping(value = "/admin/tariffs")
    public String saveTariff(HttpSession session) {
        Tariff tariff = (Tariff) session.getAttribute(TARIFF);
        tariffService.add(tariff);
        return "redirect:/admin/tariffs";
    }

    @GetMapping(value = "/tariffs")
    public @ResponseBody
    List<TariffDto> getTariffs() {
        return tariffService.getAll();
    }

    @GetMapping("/admin/tariffs")
    public String showTariffs(Model model) {
        model.addAttribute("tariffs", tariffService.getAll());
        return "admin/tariffs";
    }

    @PostMapping(value = "/admin/tariffs/delete/{tariffId}")
    public String deleteTariff(@PathVariable long tariffId) {
        tariffService.delete(tariffService.getById(tariffId));
        return "redirect:/admin/tariffs";
    }

    @GetMapping(value = "/admin/tariffs/edit/{tariffId}")
    public String editTariffName(@PathVariable long tariffId, Model model) {
        Tariff tariff = tariffService.getById(tariffId);
        model.addAttribute("editedTariff", tariff);
        return "/admin/editTariff";
    }

    @PostMapping(value = "/admin/tariffs/edit")
    public String saveEditedTariffName(@ModelAttribute("editedTariff") Tariff edited) {
        tariffService.edit(edited);
        return "redirect:/admin/tariffs";
    }

    @PostMapping("/admin/tariffs/put")
    public String putTariff(HttpSession session) {
        Tariff tariff = (Tariff) session.getAttribute(TARIFF);
        tariffService.edit(tariff);
        return "redirect:/admin/tariffs";
    }

    /**
     * This method is used to show options are available to connect to tariff.
     * Already connected options are also shown.
     * @param session
     * @param model
     * @return view with tariff's options
     */
    @GetMapping(value = "/admin/tariffs/options")
    public String showOptionsForTariffToAdd(HttpSession session, Model model) {
        Tariff tariff = (Tariff) session.getAttribute(TARIFF);
        model.addAttribute(TARIFF, tariff);
        model.addAttribute("options", optionService.getAllNotAddedToTariff(tariff));
        model.addAttribute("selectedOptions", tariff.getOptions());
        return "/admin/chooseOptions";
    }

    @PostMapping(value = "/admin/tariffs/options/{optionId}")
    public String addOption(@PathVariable long optionId, HttpSession session) {
        Tariff tariff = (Tariff) session.getAttribute(TARIFF);
        Option option = optionService.getById(optionId);
        tariffService.addOption(tariff, option);
        return "redirect:/admin/tariffs/options";
    }

    /**
     * This method is used to delete options from tariff
     * If the option is the last in the tariff it won't be deleted
     * @param optionId Deleted option's id.
     * @return tariff's options list
     */
    @PostMapping(value = "/admin/tariffs/options/delete/{optionId}")
    public String deleteOption(@PathVariable long optionId, HttpSession session) {
        Option option = optionService.getById(optionId);
        Tariff tariff = (Tariff) session.getAttribute(TARIFF);
        tariffService.deleteOption(tariff, option);
        return "redirect:/admin/tariffs/options";
    }
}
