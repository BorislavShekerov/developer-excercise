package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.models.ShoppingCart;
import com.exercise.cloudruid.services.contracts.DealsService;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.services.contracts.ShopTillService;
import com.exercise.cloudruid.utils.enums.Deals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopTillServiceImpl implements ShopTillService {

    private final DealsService dealsService;

    private final GroceriesService groceriesService;

    public final ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());

    @Autowired
    public ShopTillServiceImpl(DealsService dealsService, GroceriesService groceriesService) {
        this.dealsService = dealsService;
        this.groceriesService = groceriesService;
    }


    @Override
    public List<Groceries> viewCart() {
        List<Groceries> groceriesList = new ArrayList<>();
        for (List<Groceries> key : shoppingCart.getCart().values()) {
            groceriesList.addAll(key);
        }
        return groceriesList;
    }

    @Override
    public void addToCart(String itemName) {
        Groceries item = groceriesService.getByName(itemName);
        if (shoppingCart.getCart().get(Deals.TWOFORTHREE).size() == 3)
            shoppingCart.getCart().get(Deals.TWOFORTHREE).add(item);
        else if (item.getDeal() == Deals.TWOFORTHREE)
            shoppingCart.getCart().get(Deals.NONE).add(item);
        else shoppingCart.getCart().get(item.getDeal()).add(item);
        totalCostOfCart();
    }

    @Override
    public void removeFromCart(String itemName) {
        Groceries item = groceriesService.getByName(itemName);
        shoppingCart.getCart().get(item.getDeal()).remove(item);
        totalCostOfCart();
    }

    @Override
    public void scanListOfItems(List<String> itemNames) {
        for (String itemName : itemNames) {
            addToCart(itemName);
        }
    }

    @Override
    public void emptyShoppingCart() {
        shoppingCart.getCart().get(Deals.NONE).clear();
        shoppingCart.getCart().get(Deals.BUYONEGETONEHALFPRICE).clear();
        shoppingCart.getCart().get(Deals.TWOFORTHREE).clear();
    }

    @Override
    public String totalCostOfCart() {
        int price = 0;
        price += dealsService.buyOneGetOneHalfPrice(shoppingCart.getCart().get(Deals.BUYONEGETONEHALFPRICE));
        price += dealsService.twoForThree(shoppingCart.getCart().get(Deals.TWOFORTHREE));
        for (Groceries groceries : shoppingCart.getCart().get(Deals.NONE)) {
            price += groceries.getPrice();
        }
        String[] stringOfPrice = String.valueOf(price / 100).split(",");
        return String.format("%s aws and %s clouds", stringOfPrice[0], stringOfPrice[1]);
    }
}
