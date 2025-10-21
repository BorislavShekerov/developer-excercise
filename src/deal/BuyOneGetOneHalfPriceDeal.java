package deal;

import product.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuyOneGetOneHalfPriceDeal implements Deal {
    private Set<Product> products;

    BuyOneGetOneHalfPriceDeal() {
        this.products = new HashSet<>();
    }

    BuyOneGetOneHalfPriceDeal(List<Product> productsList) {
        this.products = new HashSet<>();
        this.products.addAll(productsList);
    }

    @Override
    public boolean qualifiesForDeal(List<Product> productsList) {
        int counter = 0;
        for (Product currentProduct: productsList) {
            if (products.contains(currentProduct)) {
                counter++;
            }
        }
        return counter > 0;
    }

    @Override
    public double calculateDiscount(List<Product> productsList) {
        double price = 0;
        Map<Product, Integer> discountCounter = initDiscountMap();
        for (Product currentProduct: productsList) {
            if (products.contains(currentProduct)) {
                Integer currentCount = discountCounter.get(currentProduct);
                currentCount++;

                if (currentCount == 2) {
                    double discount = currentProduct.price() * 2 - currentProduct.price() * 1.5;
                    price += discount;
                    currentCount = 0;
                }
                discountCounter.replace(currentProduct, currentCount);
            }
        }
        return price;
    }

    private Map<Product, Integer> initDiscountMap() {
        Map<Product, Integer> discountMap = new HashMap<>();
        for (Product currentProduct: products) {
            discountMap.put(currentProduct, 0);
        }
        return discountMap;
    }
}
