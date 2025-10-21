package com.yostoya.shoptill.service.decorator;


import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.service.deal.IDeal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FiftyOffDecorator implements IDeal {

    private final IDeal deal;

    public FiftyOffDecorator(final IDeal deal) {
        Objects.requireNonNull(deal);
        this.deal = deal;
    }

    @Override
    public BigDecimal calculateTotals(List<Item> items) {
        final BigDecimal total = deal.calculateTotals(items).multiply(BigDecimal.valueOf(100));

        final Map<Item, Long> aggregated = items.stream().filter(Item::isHalfPrice).collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        double discount = 0;

        for (Map.Entry<Item, Long> entry : aggregated.entrySet()) {
            final Item i = entry.getKey();
            final Long o = entry.getValue();

            double currentPrice = i.getPrice().doubleValue();

            discount += (o / 2.0) * (currentPrice * 0.5);
        }
        // p, p, t, p
        // p, p, p

        return total.subtract(BigDecimal.valueOf(discount)).setScale(2, RoundingMode.HALF_EVEN).divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }
}
