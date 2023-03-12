package com.exercise.cloudruid.services.contracts;

import com.exercise.cloudruid.models.Groceries;

import java.util.List;

public interface ShopTillService {

     List<Groceries> viewCart();

    void addToCart(String itemName);

    void removeFromCart(String itemName);

    void scanListOfItems(List<String> items);

    void emptyShoppingCart();

    String totalCostOfCart();
}


