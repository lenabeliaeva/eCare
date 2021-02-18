package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.dao.OptionDao;
import com.example.demo.dao.TariffDao;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    OptionDao optionDao;

    @Autowired
    TariffDao tariffDao;

    @Autowired
    ContractDao contractDao;

    @Override
    public void changeTariff(Cart cart, long tariffId, long contractId) {
        Tariff tariff = tariffDao.getById(tariffId);
        CartItem cartItem = cart.getItemByContract(contractId);
        if (cartItem == null) {
            cartItem = createCartItem(contractId);
            cart.addItem(cartItem);
        }
        cartItem.setTariff(tariff);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
    }

    @Override
    public void addOption(Cart cart, long optionId, long contractId) {
        Option option = optionDao.getById(optionId);
        CartItem cartItem = cart.getItemByContract(contractId);
        if (cartItem == null) {
            cartItem = createCartItem(contractId);
            cart.addItem(cartItem);
        }
        cartItem.addOption(option);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
    }

    @Override
    public void deleteOption(Cart cart, long optionId, long contractId) {
        Option option = optionDao.getById(optionId);
        CartItem cartItem = cart.getItemByContract(contractId);
        cartItem.deleteOption(option);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
    }

    @Override
    public void deleteCartItem(Cart cart, long contractId) {
        cart.deleteItem(contractId);
    }

    @Override
    public void submitAndAddToContract(Cart cart) {
        Contract contract;
        for (CartItem item:
             cart.getCartItems()) {
            contract = item.getContract();
            contract.setTariff(item.getTariff());
            contract.setOption(item.getOptions());
            contract.setTariffPrice(item.getPrice());
            contract.setConnectionCost(item.getConnectionCost());
            contractDao.update(contract);
        }
        cart.getCartItems().clear();
    }

    private double updateCartItemPrice(CartItem cartItem) {
        double newPrice = 0;
        Tariff tariff = cartItem.getTariff();
        Set<Option> options = cartItem.getOptions();
        if (options != null) {
            for (Option option :
                    options) {
                newPrice += option.getPrice();
            }
        }
        if (tariff != null) {
            newPrice += tariff.getPrice();
        }
        return newPrice;
    }

    private double updateCartItemConnectionCost(CartItem cartItem) {
        double newConnectionCost = 0;
        Tariff tariff = cartItem.getTariff();
        Set<Option> options = cartItem.getOptions();
        if (options != null) {
            for (Option option :
                    options) {
                newConnectionCost += option.getConnectionCost();
            }
        }
        if (tariff != null) {
            for (Option option :
                    tariff.getOptions()) {
                newConnectionCost += option.getConnectionCost();
            }
        }
        return newConnectionCost;
    }

    private CartItem createCartItem(long contractId) {
        CartItem cartItem = new CartItem();
        Contract contract = contractDao.getById(contractId);
        Tariff tariff = contract.getTariff();
        Set<Option> options = new HashSet<>(contract.getOption());
        cartItem.setContract(contract);
        cartItem.setTariff(tariff);
        cartItem.setOptions(options);
        return cartItem;
    }
}
