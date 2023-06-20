package com.yostoya.shoptill.service.deal;



import com.yostoya.shoptill.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public interface IDeal {
    BigDecimal calculateTotals(final List<Item> items);
}
