package com.yostoya.shoptill.service.strategy;


import com.yostoya.shoptill.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public interface Strategy {

    BigDecimal calculate(final List<Item> items);

}
