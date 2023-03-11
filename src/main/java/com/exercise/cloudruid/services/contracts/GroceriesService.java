package com.exercise.cloudruid.services.contracts;

import com.exercise.cloudruid.models.Groceries;

import java.util.List;

public interface GroceriesService {

    List<Groceries> getAll();

    List<Groceries> getByPrice(int price, String lowerOrHigher);

    Groceries getById(int id);

    Groceries getByName(String name);

    void create(Groceries item);

    Groceries update(Groceries item);

    void delete(Groceries item);
}
