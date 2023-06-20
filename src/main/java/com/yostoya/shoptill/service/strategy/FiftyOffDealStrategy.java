package com.yostoya.shoptill.service.strategy;


import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.service.deal.BaseDeal;
import com.yostoya.shoptill.service.decorator.FiftyOffDecorator;

import java.math.BigDecimal;
import java.util.List;

public class FiftyOffDealStrategy implements Strategy {

    @Override
    public BigDecimal calculate(final List<Item> items) {
        return new FiftyOffDecorator(new BaseDeal()).calculateTotals(items);
    }
}
