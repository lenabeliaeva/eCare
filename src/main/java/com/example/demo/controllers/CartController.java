package com.example.demo.controllers;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    CartService service;

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            model.addAttribute("cart", cart);
        } else {
            session.setAttribute("cart", new Cart());
        }
        return "client/cart";
    }

    @PostMapping("/cart")
    public String submit(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        service.submitAndAddToContract(cart);
        return "redirect:/";
    }

    @PostMapping("/cart/connectOption/{optionId}/{contractId}")
    public String connectOption(@PathVariable long optionId, @PathVariable long contractId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        service.addOption(cart, optionId, contractId);
        return "redirect:/profile/connectOptions/{contractId}";
    }

    @PostMapping("/cart/deleteOption/{optionId}/{contractId}")
    public String deleteOption(@PathVariable long optionId, @PathVariable long contractId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        service.deleteOption(cart, optionId, contractId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/connectTariff/{tariffId}/{contractId}")
    public String connectTariff(@PathVariable long tariffId, @PathVariable long contractId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        service.changeTariff(cart, tariffId, contractId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/deleteItem/{contractId}")
    public String deleteItem(@PathVariable long contractId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        service.deleteCartItem(cart, contractId);
        return "redirect:/cart";
    }
}
