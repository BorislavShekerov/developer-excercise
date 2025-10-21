package com.yostoya.shoptill.service.deal;



import com.yostoya.shoptill.domain.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BaseDeal implements IDeal {
    @Override
    public BigDecimal calculateTotals(final List<Item> items) {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }
}
