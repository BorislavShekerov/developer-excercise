package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.models.ShoppingCart;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.services.contracts.ShopTillService;
import com.exercise.cloudruid.utils.enums.Deals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopTillServiceImpl implements ShopTillService {

    private final GroceriesService groceriesService;

    public static final ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());

    @Autowired
    public ShopTillServiceImpl(GroceriesService groceriesService) {
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
        price += buyOneGetOneHalfPrice(shoppingCart.getCart().get(Deals.BUYONEGETONEHALFPRICE));
        price += twoForThree(shoppingCart.getCart().get(Deals.TWOFORTHREE));
        for (Groceries groceries : shoppingCart.getCart().get(Deals.NONE)) {
            price += groceries.getPrice();
        }
        String[] stringOfPrice = String.valueOf(price / 100).split(",");
        return String.format("%s aws and %s clouds", stringOfPrice[0], stringOfPrice[1]);
    }

    private int twoForThree(List<Groceries> items) {
        if (items.get(0).getPrice() < items.get(1).getPrice()
                && items.get(0).getPrice() < items.get(2).getPrice()) {
            return items.get(1).getPrice() + items.get(2).getPrice();
        } else if (items.get(1).getPrice() < items.get(2).getPrice()) {
            return items.get(0).getPrice() + items.get(2).getPrice();
        } else {
            return items.get(0).getPrice() + items.get(1).getPrice();
        }
    }

    private int buyOneGetOneHalfPrice(List<Groceries> items) {
        if (items.isEmpty())
            return 0;
        else if (items.size() == 1)
            return items.get(0).getPrice();
        int finalPrice = 0;
        Map<String, Integer> promoCount= new HashMap<>();
        for (Groceries item : items) {
            if (!promoCount.containsKey(item.getName()))
                promoCount.put(item.getName(), 1);
            else
                promoCount.put(item.getName(), promoCount.get(item.getName())+1);
        }
        for (Map.Entry<String, Integer> s : promoCount.entrySet()) {
            for (int i = 0; i < s.getValue(); i++) {
                if (promoCount.get(s)%2 == 1)
                    finalPrice += groceriesService.getByName(s.getKey()).getPrice();
                else finalPrice += groceriesService.getByName(s.getKey()).getPrice()/2;
            }
        }
        return finalPrice;
    }
}
