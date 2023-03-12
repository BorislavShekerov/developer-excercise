package com.exercise.cloudruid.services.contracts;

import java.util.List;

public interface DealsService {

    void addToDealTwoForThree(List<String> itemNames);

    void addToDealBuyOneGetOneHalfPrice(List<String> itemNames);

    void removeFromPromotion(List<String> itemNames);
}
