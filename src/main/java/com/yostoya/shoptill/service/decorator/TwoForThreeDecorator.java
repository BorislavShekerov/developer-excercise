package com.yostoya.shoptill.service.decorator;


import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.service.deal.IDeal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TwoForThreeDecorator implements IDeal {

    private final IDeal deal;

    public TwoForThreeDecorator(IDeal deal) {
        Objects.requireNonNull(deal);
        this.deal = deal;
    }

    @Override
    public BigDecimal calculateTotals(final List<Item> items) {
        final BigDecimal total = deal.calculateTotals(items).multiply(BigDecimal.valueOf(100));
        final List<Item> firstThree = items.subList(0, 3);
        final Optional<BigDecimal> discount = firstThree.stream().map(Item::getPrice).min(Comparator.naturalOrder());

        return total.subtract(discount.orElseThrow()).setScale(2, RoundingMode.HALF_EVEN).divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }
}
