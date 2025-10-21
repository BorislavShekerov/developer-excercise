package com.yostoya.shoptill.service.strategy;


import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.service.deal.BaseDeal;

import java.math.BigDecimal;
import java.util.List;

public class BaseDealStrategy implements Strategy {

    @Override
    public BigDecimal calculate(final List<Item> items) {
        return new BaseDeal().calculateTotals(items);
    }
}
