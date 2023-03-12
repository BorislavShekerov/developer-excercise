package com.exercise.cloudruid.models;

import com.exercise.cloudruid.utils.enums.Deals;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Groceries")
@Table(name = "groceries")
public class Groceries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "deal")
    private Enum<Deals> deal;
}
