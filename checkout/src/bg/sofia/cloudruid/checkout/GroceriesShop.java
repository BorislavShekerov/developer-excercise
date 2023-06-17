package bg.sofia.cloudruid.checkout;

import bg.sofia.cloudruid.checkout.exception.InvalidProductException;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class GroceriesShop implements GroceriesShopAPI {

    private final Map<String, Integer> products;

    private final Map<String, Boolean> productsInDiscount;
    private final Set<Set<String>> discountsTwoForThree;

    private final Set<String> discountsBuyOneGetHalfOff;

    public GroceriesShop() {
        this.products = new HashMap<>();
        this.productsInDiscount = new HashMap<>();
        this.discountsTwoForThree = new HashSet<>();
        this.discountsBuyOneGetHalfOff = new HashSet<>();
    }

    @Override
    public boolean addProduct(String productName, int price) {
        if (productName == null || productName.isBlank() || price < 0) {
            throw new IllegalArgumentException("Invalid product name or price");
        }

        if (products.containsKey(productName)) {
            return false;
        }

        products.put(productName, price);
        productsInDiscount.put(productName, false);

        return true;
    }

    @Override
    public boolean addDiscountTwoForThree(String productName, String productName2, String productName3)
        throws InvalidProductException {
        if (productName == null || productName.isBlank() || productName2 == null || productName2.isBlank() ||
            productName3 == null || productName3.isBlank()) {
            throw new IllegalArgumentException("Invalid product name");
        }

        if (!products.containsKey(productName) || !products.containsKey(productName2) ||
            !products.containsKey(productName3)) {
            throw new InvalidProductException("Product does not exist");
        }

        if (productsInDiscount.get(productName) || productsInDiscount.get(productName2) ||
            productsInDiscount.get(productName3)) {
            return false;
        }

        Set<String> discount = new HashSet<>();
        discount.add(productName);
        discount.add(productName2);
        discount.add(productName3);

        productsInDiscount.put(productName, true);
        productsInDiscount.put(productName2, true);
        productsInDiscount.put(productName3, true);

        discountsTwoForThree.add(discount);

        return true;
    }

    @Override
    public boolean addDiscountBuyOneGetHalfOff(String productName) throws InvalidProductException {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Invalid product name");
        }

        if (!products.containsKey(productName)) {
            throw new InvalidProductException("Product does not exist");
        }

        if (productsInDiscount.get(productName)) {
            return false;
        }

        productsInDiscount.put(productName, true);
        discountsBuyOneGetHalfOff.add(productName);

        return true;
    }

    @Override
    public Map<String, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public Set<Set<String>> getDiscountsTwoForThree() {
        return Collections.unmodifiableSet(discountsTwoForThree);
    }

    @Override
    public Set<String> getDiscountsBuyOneGetHalfOff() {
        return Collections.unmodifiableSet(discountsBuyOneGetHalfOff);
    }

    @Override
    public boolean isInDiscount(String productName) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Invalid product name");
        }

        if (!products.containsKey(productName)) {
            throw new InvalidProductException("Product does not exist");
        }

        return productsInDiscount.get(productName);

    }
}
