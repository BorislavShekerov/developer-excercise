package com.yostoya.shoptill.service.facade;

import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.service.strategy.*;

import java.math.BigDecimal;
import java.util.List;


public class TillFacadeImpl implements TillFacade {

    @Override
    public BigDecimal calcTotal(final List<Item> scannedItems) {

        final boolean hasFiftyOffDeal = scannedItems.stream().anyMatch(Item::isHalfPrice);

        Context ctx;
        if (scannedItems.size() <= 1) {
            ctx = new Context(new BaseDealStrategy());
        } else {

            if (scannedItems.size() >= 3) {

                if (hasFiftyOffDeal) {
                    ctx = new Context(new DoubleDiscountDealStrategy());
                } else {
                    ctx = new Context(new TwoForThreeDealStrategy());
                }

            } else {

                if (hasFiftyOffDeal) {
                    ctx = new Context(new FiftyOffDealStrategy());
                } else {
                    ctx = new Context(new BaseDealStrategy());
                }
            }
        }

        return ctx.executeStrategy(scannedItems);
    }
}
