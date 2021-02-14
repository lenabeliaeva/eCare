package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Cart {
    @Getter
    @Setter
    private double totalPrice;

    @Setter
    private List<CartItem> cartItems = new LinkedList<>();

    public List<CartItem> getCartItems() {
        cartItems.removeIf(item ->
                (item.getOptions().isEmpty() || item.getOptions() == null) && item.getTariff() == null);
        return cartItems;
    }

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
