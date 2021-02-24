package com.example.demo.controllers;

import com.example.demo.exceptions.OptionsDependentException;
import com.example.demo.exceptions.OptionsIncompatibleException;
import com.example.demo.models.Cart;
import com.example.demo.models.Contract;
import com.example.demo.services.CartService;
import com.example.demo.services.ContractService;
import com.example.demo.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    private static final String CART = "cart";

    @Autowired
    CartService service;

    @Autowired
    ContractService contractService;

    @Autowired
    OptionService optionService;

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        Cart cart = getCartFromSession(session);
        model.addAttribute(CART, cart);
        return "contract/cart";
    }

    @PostMapping("/cart")
    public String submit(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        service.submitAndAddToContract(cart);
        return "redirect:/";
    }

    /**
     * This method is used to show available for connection options
     * @param contractId to get Contract which is modified
     * @param model to show options
     * @return view with options list
     */
    @GetMapping(value = "/contract/connectOptions/{contractId}")
    public String showOptions(@PathVariable long contractId, Model model) {
        showAvailableOptions(contractId, model);
        return "/contract/addOptionsToContract";
    }

    @PostMapping("/cart/connectOption/{optionId}/{contractId}")
    public String connectOption(@PathVariable long optionId, @PathVariable long contractId, HttpSession session,
                                Model model) {
        Cart cart = getCartFromSession(session);
        try {
            service.addOption(cart, optionId, contractId);
            model.addAttribute("msg", "Option is successfully added to the cart");
            return "redirect:/contract/connectOptions/{contractId}";
        } catch (OptionsIncompatibleException | OptionsDependentException e) {
            model.addAttribute("msg", e.getMessage());
            showAvailableOptions(contractId, model);
            return "/contract/addOptionsToContract";
        }
    }

    @PostMapping("/cart/deleteOption/{optionId}/{contractId}")
    public String deleteOption(@PathVariable long optionId, @PathVariable long contractId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        service.deleteOption(cart, optionId, contractId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/connectTariff/{tariffId}/{contractId}")
    public String connectTariff(@PathVariable long tariffId, @PathVariable long contractId, HttpSession session) {
        Cart cart = getCartFromSession(session);
        service.changeTariff(cart, tariffId, contractId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/deleteItem/{contractId}")
    public String deleteItem(@PathVariable long contractId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        service.deleteCartItem(cart, contractId);
        return "redirect:/cart";
    }

    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private void showAvailableOptions(long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);
        model.addAttribute("availableOptions", optionService.getAllNotAddedToContractOptions(contract));
        model.addAttribute("contract", contract);
    }
}
