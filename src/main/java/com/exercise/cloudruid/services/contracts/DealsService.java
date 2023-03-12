package com.exercise.cloudruid.services.contracts;

import com.exercise.cloudruid.models.Groceries;

import java.util.List;

public interface DealsService {

    void addToDealTwoForThree(List<String> itemNames);

    void addToDealBuyOneGetOneHalfPrice(List<String> itemNames);

    void removeFromPomotion(List<String> itemNames);

    int twoForThree(List<Groceries> items);

    int buyOneGetOneHalfPrice(List<Groceries> items);

}
