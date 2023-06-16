package bg.sofia.cloudruid.checkout.customer;

import bg.sofia.cloudruid.checkout.GroceriesShopAPI;
import bg.sofia.cloudruid.checkout.discount.BuyOneGetHalfOff;
import bg.sofia.cloudruid.checkout.discount.TwoForThreeDiscount;
import bg.sofia.cloudruid.checkout.exception.InvalidProductException;
import bg.sofia.cloudruid.checkout.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Customer implements CustomerAPI {

    private Map<Product, Integer> cart;
    GroceriesShopAPI groceriesShop;

    public Customer(GroceriesShopAPI groceriesShop) {
        cart = new HashMap<>();
        this.groceriesShop = groceriesShop;
    }

    @Override
    public void addToCart(String productName, int quantity) {
        if (productName == null || productName.isEmpty() || quantity < 0) {
            throw new IllegalArgumentException();
        }

        Product product = groceriesShop.getProducts().stream()
            .filter(p -> p.name().equals(productName))
            .findFirst()
            .orElse(null);

        if (product == null) {
            throw new InvalidProductException("Product not found");
        }

        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + quantity);
        } else {
            cart.put(product, quantity);
        }
    }

    @Override
    public void removeFromCart(String productName, int quantity) {
        if (productName == null || productName.isEmpty() || quantity < 0) {
            throw new IllegalArgumentException();
        }

        Product product = groceriesShop.getProducts().stream()
            .filter(p -> p.name().equals(productName))
            .findFirst()
            .orElse(null);

        if (product == null) {
            throw new InvalidProductException("Product not found");
        }

        if (cart.containsKey(product) && cart.get(product) >= quantity) {
            if (cart.get(product) == quantity) {
                cart.remove(product);
            } else {
                cart.put(product, cart.get(product) - quantity);
            }
        } else {
            throw new InvalidProductException("Product not found or not enough quantity");
        }

    }

    @Override
    public int checkout() {
        int total = 0;


        for (TwoForThreeDiscount discount : groceriesShop.getDiscountsTwoForThree()) {

            Product p1 = cart.keySet().stream()
                .filter(product -> product.name().equals(discount.productName1()))
                .findFirst()
                .orElse(null);

            Product p2 = cart.keySet().stream()
                .filter(product -> product.name().equals(discount.productName2()))
                .findFirst()
                .orElse(null);
            Product p3 = cart.keySet().stream()
                .filter(product -> product.name().equals(discount.productName3()))
                .findFirst()
                .orElse(null);

            if (p1 != null && p2 != null && p3 != null) {
                int minQuantity = Math.min(cart.get(p1),
                    Math.min(cart.get(p2), cart.get(p3)));


                int cheapestProduct = Math.min(p1.price(), Math.min(p2.price(), p3.price()));

                if (p1.price() == cheapestProduct) {
                    cart.put(p1, cart.get(p1) - minQuantity);
                } else if (p2.price() == cheapestProduct) {
                    cart.put(p2, cart.get(p2) - minQuantity);
                } else {
                    cart.put(p3, cart.get(p3) - minQuantity);
                }

                total += cart.get(p1) * p1.price();
                total += cart.get(p2) * p2.price();
                total += cart.get(p3) * p3.price();
                cart.remove(p1);
                cart.remove(p2);
                cart.remove(p3);
            }
        }


        for (BuyOneGetHalfOff discount : groceriesShop.getDiscountsBuyOneGetHalfOff()) {
            Product p1 = cart.keySet().stream()
                .filter(product -> product.name().equals(discount.productName()))
                .findFirst()
                .orElse(null);

            if (p1 != null) {
                int minQuantity = cart.get(p1) / 2;
                total += minQuantity * (p1.price() / 2);
                cart.put(p1, cart.get(p1) - minQuantity);
                total += cart.get(p1) * p1.price();
                cart.remove(p1);
            }
        }


        for (Product product : cart.keySet()) {
            total += cart.get(product) * product.price();
            cart.remove(product);
        }

        return total;
    }

    @Override
    public void listCart() {
        cart.forEach((product, quantity) -> System.out.println(
            product.name() + " " + quantity + " for " + product.price() + " each"));
    }
}
