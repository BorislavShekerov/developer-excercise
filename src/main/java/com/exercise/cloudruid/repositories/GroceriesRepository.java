package com.exercise.cloudruid.repositories;

import com.exercise.cloudruid.models.Groceries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroceriesRepository extends JpaRepository<Groceries, Integer> {
    Optional<Groceries> findByName(String name);

    boolean existsByName(String name);
}
