package deal;

import product.Product;
import java.util.List;

public class DealCreator {
    public Deal createDeal(DealName dealName, List<Product> products) {
        return switch (dealName) {
            case BUY_ONE_GET_ONE_HALF_PRICE -> new BuyOneGetOneHalfPriceDeal(products);
            case TWO_FOR_THREE_DEAL -> new TwoForThreeDeal(products);
            case null -> null;
        };
    }
}
