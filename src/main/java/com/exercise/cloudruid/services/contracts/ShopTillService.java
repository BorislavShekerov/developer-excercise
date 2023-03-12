package com.exercise.cloudruid.services.contracts;

import com.exercise.cloudruid.models.Groceries;

import java.util.List;

public interface ShopTillService {

     List<Groceries> viewCart();

    void addToCart(Groceries item);

    void removeFromCart(Groceries items);

    void scanListOfItems(List<Groceries> items);

    void emptyShoppingCart();

    String totalCostOfCart();
}


