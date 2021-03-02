package com.example.demo.services.api;

import com.example.demo.exceptions.OptionsDependentException;
import com.example.demo.exceptions.OptionsIncompatibleException;
import com.example.demo.models.Cart;
import com.example.demo.models.entities.Option;

import java.util.List;

public interface CartService {
    void changeTariff(Cart cart, long tariffId, long contractId);

    void addOption(Cart cart, long optionId, long contractId) throws OptionsIncompatibleException, OptionsDependentException;

    void deleteOption(Cart cart, long optionId, long contractId);

    void deleteCartItem(Cart cart, long contractId);

    void submitAndAddToContract(Cart cart);

    List<Option> getNotAddedCartItemContractOptions(Cart cart, long contractId);
}
