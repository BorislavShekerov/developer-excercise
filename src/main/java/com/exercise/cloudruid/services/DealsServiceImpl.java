package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.services.contracts.DealsService;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.utils.enums.Deals;
import com.exercise.cloudruid.utils.exceptions.ItemDealException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void removeFromPromotion(List<String> itemNames) {
        for (String itemName : itemNames) {
            Groceries item = groceriesService.getByName(itemName);
            item.setDeal(Deals.NONE);
        }
    }
}
