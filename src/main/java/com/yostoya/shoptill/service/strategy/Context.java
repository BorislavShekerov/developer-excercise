package com.yostoya.shoptill.service.strategy;


import com.yostoya.shoptill.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public class Context {

    private final Strategy strategy;

    public Context(final Strategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal executeStrategy(final List<Item> items) {
        return strategy.calculate(items);
    }
}
