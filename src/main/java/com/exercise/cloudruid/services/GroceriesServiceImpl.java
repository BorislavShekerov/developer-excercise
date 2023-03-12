package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.repositories.GroceriesRepository;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exercise.cloudruid.utils.exceptions.GrocerieNotFoundException;
import com.exercise.cloudruid.utils.exceptions.ItemExistsException;

import java.util.List;

@Service
public class GroceriesServiceImpl implements GroceriesService {

    private final GroceriesRepository repository;

    @Autowired
    public GroceriesServiceImpl(GroceriesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Groceries> getAll() {
        return repository.findAll();
    }

    @Override
    public Groceries getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new GrocerieNotFoundException("Item with id: " + id + " does not exist."));
    }

    @Override
    public Groceries getByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new GrocerieNotFoundException("Item with name: " + name + " does not exist."));
    }

    @Override
    public void create(Groceries item) {
        if (repository.existsByName(item.getName()))
            throw new ItemExistsException("Item with name: " + item.getName() + " already exists");

        repository.saveAndFlush(item);
    }

    //TODO test to see if this actually works
    @Override
    public Groceries update(Groceries item) {
        return repository.saveAndFlush(item);
    }

    @Override
    public void delete(Groceries item) {
        repository.delete(item);
    }
}
