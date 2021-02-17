package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Cart {

    private double totalPrice;

    private List<CartItem> cartItems = new LinkedList<>();

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public void deleteItem(long contractId) {
        cartItems.removeIf(item1 -> item1.getContract().getId() == contractId);
    }

    public CartItem getItemByContract(long contractId) {
        return cartItems
                .stream()
                .filter(item -> item.getContract().getId() == contractId)
                .findFirst()
                .orElse(null);

    }
}
