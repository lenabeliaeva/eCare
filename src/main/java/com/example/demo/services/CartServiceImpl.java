package com.example.demo.services;

import com.example.demo.dao.ContractDao;
import com.example.demo.dao.OptionDao;
import com.example.demo.dao.TariffDao;
import com.example.demo.exceptions.OptionsDependentException;
import com.example.demo.exceptions.OptionsIncompatibleException;
import com.example.demo.models.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    OptionDao optionDao;

    @Autowired
    TariffDao tariffDao;

    @Autowired
    ContractDao contractDao;

    /**
     * This method adds Tariff to the Cart.
     * Each cart item holds contract which is need to be changed.
     * If the cart hasn't got item for the contract, new cart item has to be created to save tariff there.
     *
     * @param cart       from session
     * @param tariffId   to get Tariff
     * @param contractId to get Contract
     */
    @Override
    public void changeTariff(Cart cart, long tariffId, long contractId) {
        Tariff tariff = tariffDao.getById(tariffId);
        CartItem cartItem = cart.getItemByContract(contractId);
        if (cartItem == null) {
            cartItem = createCartItem(contractId);
            cart.addItem(cartItem);
            log.info("New cart item is created to add tariff");
        }
        cartItem.setTariff(tariff);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
        log.info("Tariff is added to the cart");
    }

    /**
     * This method is used to add an option to a cart.
     * First of all, the option is checked for compatibility with other options.
     * Then, dependent options are checked.
     * Each cart item holds contract which is need to be changed.
     * If the cart hasn't got item for the contract, new cart item has to be created to save option there.
     *
     * @param cart       Cart object from session
     * @param optionId   to get Option entity
     * @param contractId to get Contract entity
     */
    @Override
    public void addOption(Cart cart, long optionId, long contractId) throws OptionsIncompatibleException,
            OptionsDependentException {
        Option option = optionDao.getById(optionId);
        Contract contract = contractDao.getById(contractId);
        Set<Option> allCartItemOptions = new HashSet<>(contract.getOption());
        allCartItemOptions.addAll(contract.getTariff().getOptions());
        if (!option.isCompatibleWith(allCartItemOptions)) {
            throw new OptionsIncompatibleException("Option is incompatible with the added options");
        }
        if (!option.isDependentFrom(allCartItemOptions)) {
            throw new OptionsDependentException("Option is dependent from other options");
        }
        CartItem cartItem = cart.getItemByContract(contractId);
        if (cartItem == null) {
            cartItem = createCartItem(contractId);
            cart.addItem(cartItem);
            log.info("New cart item is created to add the option " + option.getName());
        }
        cartItem.addOption(option);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
        log.info("Option " + option.getName() + " is added to the cart");

    }

    /**
     * When option is deleted price and connection cost in cart item should be recalculated
     *
     * @param cart       from session.
     * @param optionId   to get Option to add to the cart
     * @param contractId to get cart item for the contract
     */
    @Override
    public void deleteOption(Cart cart, long optionId, long contractId) {
        Option option = optionDao.getById(optionId);
        CartItem cartItem = cart.getItemByContract(contractId);
        cartItem.deleteOption(option);
        cartItem.setPrice(updateCartItemPrice(cartItem));
        cartItem.setConnectionCost(updateCartItemConnectionCost(cartItem));
        log.info("Option " + option.getName() + "is deleted from the cart");
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
            contract.setPrice(item.getPrice());
            contract.setConnectionCost(item.getConnectionCost());
            contractDao.update(contract);
            log.info("Contract " + contract.getNumber() + " is updated");
        }
        cart.getCartItems().clear();
    }

    @Override
    public List<Option> getNotAddedCartItemContractOptions(Cart cart, long contractId) {
        CartItem cartItem = cart.getItemByContract(contractId);
        Set<Option> cartItemOptions;
        if (cartItem != null) {
            cartItemOptions = new HashSet<>(cartItem.getOptions());
            cartItemOptions.addAll(cartItem.getTariff().getOptions());
        } else {
            Contract contract = contractDao.getById(contractId);
            cartItemOptions = new HashSet<>(contract.getOption());
            Tariff contractTariff = contract.getTariff();
            if (contractTariff != null) {
                cartItemOptions.addAll(contractTariff.getOptions());
            }
        }
        List<Option> notAddedCartItemOptions = optionDao.getAll();
        for (Option option :
                cartItemOptions) {
            notAddedCartItemOptions.removeIf(o -> o.getId() == option.getId());
        }
        return notAddedCartItemOptions;
    }

    /**
     * CartItem price is calculated from prices of a tariff and options which it contains.
     * It has to be recalculated every time CartItem is changed.
     *
     * @param cartItem
     * @return new price
     */
    private double updateCartItemPrice(CartItem cartItem) {
        double newPrice = 0;
        Tariff tariff = cartItem.getTariff();
        Set<Option> options = cartItem.getOptions();
        if (options != null) {
            newPrice += options.stream().mapToDouble(Option::getPrice).sum();
        }
        if (tariff != null) {
            newPrice += tariff.getPrice();
        }
        return newPrice;
    }

    /**
     * CartItem connection cost is calculated from connection costs of tariff options and options which are
     * contained there.
     * It has to be recalculated every time CartItem is changed.
     *
     * @param cartItem
     * @return new connection cost
     */
    private double updateCartItemConnectionCost(CartItem cartItem) {
        double newConnectionCost = 0;
        Tariff tariff = cartItem.getTariff();
        Set<Option> options = cartItem.getOptions();
        if (options != null) {
            newConnectionCost += options.stream().mapToDouble(Option::getConnectionCost).sum();
        }
        if (tariff != null) {
            newConnectionCost += tariff.getOptions().stream().mapToDouble(Option::getConnectionCost).sum();
        }
        return newConnectionCost;
    }

    /**
     * Cart item is created for the contract which is has to be modified.
     * That is why cart item should contain all information about the contract.
     *
     * @param contractId to get contract from DB
     * @return created CartItem
     */
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
