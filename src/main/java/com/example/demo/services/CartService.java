package com.example.demo.services;

import com.example.demo.models.Cart;

public interface CartService {
    void changeTariff(Cart cart, long tariffId, long contractId);

    void addOption(Cart cart, long optionId, long contractId);

    void deleteOption(Cart cart, long optionId, long contractId);

    void deleteCartItem(Cart cart, long contractId);

    void submitAndAddToContract(Cart cart);
}
