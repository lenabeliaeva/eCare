package com.example.demo;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.dao.api.ContractDao;
import com.example.demo.dao.api.OptionDao;
import com.example.demo.dao.api.TariffDao;
import com.example.demo.exceptions.OptionsDependentException;
import com.example.demo.exceptions.OptionsIncompatibleException;
import com.example.demo.models.*;
import com.example.demo.models.entities.Contract;
import com.example.demo.models.entities.Option;
import com.example.demo.models.entities.Tariff;
import com.example.demo.services.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private TariffDao tariffDao;

    @Mock
    private OptionDao optionDao;

    @Mock
    private ContractDao contractDao;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private Tariff tariff;
    private Option option;
    private Contract contract;

    @BeforeEach
    void setup() {
        cart = new Cart();
        tariff = new Tariff();
        tariff.setId(1L);
        tariff.setPrice(100);
        option = new Option();
        option.setId(1L);
        option.setPrice(200);
        option.setConnectionCost(100);
        contract = new Contract();
        contract.setId(1L);
    }

    @Test
    void shouldAddTariffAndCreateNewCartItem() {
        when(tariffDao.getById(1L)).thenReturn(tariff);
        when(contractDao.getById(1L)).thenReturn(contract);
        cartService.changeTariff(cart, 1L, 1L);
        assertEquals(100, cart.getItemByContract(1L).getPrice());
        assertEquals(0, cart.getItemByContract(1L).getConnectionCost());
        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    void shouldAddOptionAndCreateNewCartItem() throws OptionsDependentException, OptionsIncompatibleException {
        when(optionDao.getById(1L)).thenReturn(option);
        when(contractDao.getById(1L)).thenReturn(contract);
        contract.setTariff(tariff);
        cartService.addOption(cart, 1L, 1L);
        assertEquals(300, cart.getItemByContract(1L).getPrice());
        assertEquals(100, cart.getItemByContract(1L).getConnectionCost());
        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    void shouldDeleteOption() {
        when(optionDao.getById(1L)).thenReturn(option);
        CartItem cartItem = new CartItem();
        cartItem.setContract(contract);
        cartItem.addOption(option);
        cart.addItem(cartItem);
        cartService.deleteOption(cart, 1L, 1L);
        assertEquals(0, cart.getItemByContract(1L).getPrice());
        assertEquals(0, cart.getItemByContract(1L).getConnectionCost());
    }

    @Test
    void shouldDeleteOptionFromContractWithTariff() {
        when(optionDao.getById(1L)).thenReturn(option);
        CartItem cartItem = new CartItem();
        cartItem.setContract(contract);
        cartItem.addOption(option);
        cartItem.setTariff(tariff);
        cart.addItem(cartItem);
        cartService.deleteOption(cart, 1L, 1L);
        assertEquals(100, cart.getItemByContract(1L).getPrice());
        assertEquals(0, cart.getItemByContract(1L).getConnectionCost());
    }

    @Test
    void shouldSubmitContract() {
        doNothing().when(contractDao).update(isA(Contract.class));
        CartItem cartItem = new CartItem();
        cartItem.setContract(contract);
        cartItem.addOption(option);
        cartItem.setConnectionCost(option.getConnectionCost());
        cartItem.setTariff(tariff);
        cartItem.setPrice(tariff.getPrice() + option.getPrice());
        cart.addItem(cartItem);
        cartService.submitAndAddToContract(cart);
        assertEquals(300, contract.getPrice());
        assertEquals(100, contract.getConnectionCost());
        assertEquals(0, cart.getCartItems().size());
    }
}
