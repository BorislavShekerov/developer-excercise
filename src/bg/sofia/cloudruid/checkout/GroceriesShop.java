package bg.sofia.cloudruid.checkout;

import bg.sofia.cloudruid.checkout.discount.BuyOneGetHalfOff;
import bg.sofia.cloudruid.checkout.discount.TwoForThreeDiscount;
import bg.sofia.cloudruid.checkout.exception.InvalidProductException;
import bg.sofia.cloudruid.checkout.product.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class GroceriesShop implements GroceriesShopAPI {

    private final Map<Product, Boolean> products;
    private final Set<TwoForThreeDiscount> discountsTwoForThree;

    private final Set<BuyOneGetHalfOff> discountsBuyOneGetHalfOff;

    public GroceriesShop() {
        this.products = new HashMap<>();
        this.discountsTwoForThree = new HashSet<>();
        this.discountsBuyOneGetHalfOff = new HashSet<>();
    }

    @Override
    public boolean addProduct(String productName, int price) {
        if (productName == null || productName.isBlank() || price < 0) {
            throw new IllegalArgumentException("Invalid product name or price");
        }

        if (products.keySet().stream().anyMatch(product -> product.name().equals(productName))) {
            return false;
        }

        products.put(new Product(productName, price), false);

        return true;
    }

    @Override
    public boolean addDiscountTwoForThree(String productName, String productName2, String productName3)
        throws InvalidProductException {
        if (productName == null || productName.isBlank() || productName2 == null || productName2.isBlank() ||
            productName3 == null || productName3.isBlank()) {
            throw new IllegalArgumentException("Invalid product name");
        }

        Product p1 =
            products.keySet().stream().filter(product -> product.name().equals(productName)).findFirst().orElse(null);
        Product p2 =
            products.keySet().stream().filter(product -> product.name().equals(productName2)).findFirst().orElse(null);
        Product p3 =
            products.keySet().stream().filter(product -> product.name().equals(productName3)).findFirst().orElse(null);

        if (p1 == null || p2 == null || p3 == null) {
            throw new InvalidProductException("Product does not exist");
        }

        //check if the boolean value in the map is true
        if (products.get(p1) || products.get(p2) ||
            products.get(p3)) {
            return false;
        }

        products.put(p1, true);
        products.put(p2, true);
        products.put(p3, true);

        discountsTwoForThree.add(new TwoForThreeDiscount(productName, productName2, productName3));

        return true;
    }

    @Override
    public boolean addDiscountBuyOneGetHalfOff(String productName) throws InvalidProductException {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Invalid product name");
        }

        if (products.keySet().stream().noneMatch(product -> product.name().equals(productName))) {
            throw new InvalidProductException("Product does not exist");
        }

        Product p1 =
            products.keySet().stream().filter(product -> product.name().equals(productName)).findFirst().orElse(null);

        if (p1 == null) {
            throw new InvalidProductException("Product does not exist");
        }

        if (products.get(p1)) {
            return false;
        }

        products.put(p1, true);
        discountsBuyOneGetHalfOff.add(new BuyOneGetHalfOff(productName));

        return true;
    }

    @Override
    public Set<Product> getProducts() {
        return products.keySet();
    }

    @Override
    public Set<TwoForThreeDiscount> getDiscountsTwoForThree() {
        return discountsTwoForThree;
    }

    @Override
    public Set<BuyOneGetHalfOff> getDiscountsBuyOneGetHalfOff() {
        return discountsBuyOneGetHalfOff;
    }
}
