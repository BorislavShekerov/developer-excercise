package com.exercise.cloudruid.models;

import com.exercise.cloudruid.utils.enums.Deals;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ShoppingCart {

    public ShoppingCart(Map<Deals, List<Groceries>> cart) {
        this.cart = cart;
        cart.put(Deals.NONE, new ArrayList<>());
        cart.put(Deals.BUYONEGETONEHALFPRICE, new ArrayList<>(2));
        cart.put(Deals.TWOFORTHREE, new ArrayList<>(3));
    }

    private Map<Deals, List<Groceries>> cart;

}
