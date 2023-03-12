package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.services.contracts.DealsService;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.utils.enums.Deals;
import com.exercise.cloudruid.utils.exceptions.ItemDealException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DealsServiceImpl implements DealsService {

    private final GroceriesService groceriesService;

    @Autowired
    public DealsServiceImpl(GroceriesService groceriesService) {
        this.groceriesService = groceriesService;
    }

    @Override
    public void addToDealTwoForThree(List<String> itemNames) {
        for (String itemName : itemNames) {
            Groceries item = groceriesService.getByName(itemName);
            if (item.getDeal() != Deals.NONE)
                throw new ItemDealException("Item is already included in a promotion!");
            item.setDeal(Deals.TWOFORTHREE);
            groceriesService.update(item);
        }
    }

    @Override
    public void addToDealBuyOneGetOneHalfPrice(List<String> itemNames) {
        for (String itemName : itemNames) {
            Groceries item = groceriesService.getByName(itemName);
            if (item.getDeal() != Deals.BUYONEGETONEHALFPRICE)
                throw new ItemDealException("Item is already included in a promotion!");
            item.setDeal(Deals.BUYONEGETONEHALFPRICE);
            groceriesService.update(item);
        }
    }

    @Override
    public void removeFromPomotion(List<String> itemNames) {
        for (String itemName : itemNames) {
            Groceries item = groceriesService.getByName(itemName);
            item.setDeal(Deals.NONE);
        }
    }

    @Override
    public int twoForThree(List<Groceries> items) {
        if (items.get(0).getPrice() < items.get(1).getPrice()
                && items.get(0).getPrice() < items.get(2).getPrice()) {
            return items.get(1).getPrice() + items.get(2).getPrice();
        } else if (items.get(1).getPrice() < items.get(2).getPrice()) {
            return items.get(0).getPrice() + items.get(2).getPrice();
        } else {
            return items.get(0).getPrice() + items.get(1).getPrice();
        }
    }

    @Override
    public int buyOneGetOneHalfPrice(List<Groceries> items) {
        if (items.size() == 0)
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
