package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.dao.OptionDao;
import com.example.demo.dao.TariffDao;
import com.example.demo.models.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    OptionDao optionDao;

    @Autowired
    TariffDao tariffDao;

    @Autowired
    ContractDao contractDao;

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public void changeTariff(Cart cart, long tariffId, long contractId) {
        Tariff tariff = tariffDao.getById(tariffId);
        CartItem cartItem = cart.getItemByContract(contractId);
        if (cartItem == null) {
            cartItem = createCartItem(contractId);
            cart.addItem(cartItem);
            logger.info("New cart item is created to add tariff");
        }
        cartItem.setTariff(tariff);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
        logger.info("Tariff is added to the cart");
    }

    /**
     * This method is used to add an option to a cart.
     * First of all, the option is checked for compatibility with other options.
     * If the cart hasn't got items for the contract, new cart item has to be created to save option there.
     *
     * @param cart       Cart object from session
     * @param optionId   to get Option entity
     * @param contractId to get Contract entity
     */
    @Override
    public void addOption(Cart cart, long optionId, long contractId) {
        Option option = optionDao.getById(optionId);
        Contract contract = contractDao.getById(contractId);
        Set<Option> allCartItemOptions = new HashSet<>(contract.getOption());
        allCartItemOptions.addAll(contract.getTariff().getOptions());
        if (option.isCompatibleWith(allCartItemOptions) && option.isDependentFrom(allCartItemOptions)) {
            CartItem cartItem = cart.getItemByContract(contractId);
            if (cartItem == null) {
                cartItem = createCartItem(contractId);
                cart.addItem(cartItem);
                logger.info("New cart item is created to add the option " + option.getName());
            }
            cartItem.addOption(option);
            cartItem.setPrice(updateCartItemPrice(cartItem));
            cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
            logger.info("Option " + option.getName() + " is added to the cart");
        }
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
        for (CartItem item :
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

    private boolean checkCompatibility(long optionId, Set<Option> alreadyAddedOptions) {
        for (Option option :
                alreadyAddedOptions) {
            if (option.getIncompatibleOptions()
                    .stream()
                    .anyMatch(o -> o.getId() == optionId)) {
                return false;
            }
        }
        return true;
    }
}
