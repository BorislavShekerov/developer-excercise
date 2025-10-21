package com.yostoya.shoptill.service.facade;

import com.yostoya.shoptill.domain.Item;

import java.math.BigDecimal;
import java.util.List;


public interface TillFacade {
    BigDecimal calcTotal(final List<Item> items);
}
