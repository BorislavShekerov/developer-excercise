package deal;

import product.Product;

import java.util.List;

public interface Deal {
    boolean qualifiesForDeal(List<Product> products);

    double calculateDiscount(List<Product> products);
}
