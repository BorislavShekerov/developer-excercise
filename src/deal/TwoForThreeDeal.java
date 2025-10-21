package deal;

import product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwoForThreeDeal implements Deal {

    private static final int MINIMUM_ELIGIBLE_PRODUCTS = 3;
    private Set<Product> products;

    TwoForThreeDeal() {
        this.products = new HashSet<>();
    }

    TwoForThreeDeal(List<Product> productsList) {
        this.products = new HashSet<>();
        this.products.addAll(productsList);
    }

    @Override
    public boolean qualifiesForDeal(List<Product> productList) {
        int eligibleProducts = 0;

        for (Product currentProduct: productList) {
            if (products.contains(currentProduct)) {
                eligibleProducts++;
            }
        }
        return eligibleProducts >= MINIMUM_ELIGIBLE_PRODUCTS;
    }

    @Override
    public double calculateDiscount(List<Product> productList) {
        double price = 0;
        List<Product> twoForThreeCandidates = new ArrayList<>();
        for (Product currentProduct: productList) {

            if (products.contains(currentProduct)) {
                twoForThreeCandidates.add(currentProduct);
            }

            if (twoForThreeCandidates.size() == MINIMUM_ELIGIBLE_PRODUCTS) {
                price += Collections.min(twoForThreeCandidates, Comparator.comparing(Product::price)).price();
                twoForThreeCandidates.clear();
            }

        }

        return price;
    }
}
