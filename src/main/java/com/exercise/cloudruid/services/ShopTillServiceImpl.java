package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.services.contracts.ShopTillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopTillServiceImpl implements ShopTillService {
    @Override
    public List<Groceries> viewCart() {
        return ShoppingCart.itemList;
    }

    @Override
    public void addToCart(Groceries item) {
        ShoppingCart.itemList.add(item);
        ShoppingCart.price += item.getPrice();
    }

    @Override
    public void removeFromCart(Groceries item) {
        ShoppingCart.itemList.remove(item);
        ShoppingCart.price -= item.getPrice();
    }

    @Override
    public void scanListOfItems(List<Groceries> items) {
        for (Groceries item : items) {
            ShoppingCart.itemList.add(item);
            ShoppingCart.price += item.getPrice();
        }
    }

    @Override
    public void emptyShoppingCart() {
        ShoppingCart.itemList.clear();
    }

    @Override
    public String totalCostOfCart() {
        String[] stringOfPrice = String.valueOf(ShoppingCart.price/100).split(",");
        return String.format("%s aws and %s clouds", stringOfPrice[0], stringOfPrice[1]);
    }

    private static class ShoppingCart {
        private static final List<Groceries> itemList = new ArrayList<>();
        private static double price = 0;
    }
}
