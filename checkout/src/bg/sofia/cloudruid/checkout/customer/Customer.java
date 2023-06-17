package bg.sofia.cloudruid.checkout.customer;

import bg.sofia.cloudruid.checkout.GroceriesShopAPI;
import bg.sofia.cloudruid.checkout.exception.InvalidProductException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Customer implements CustomerAPI {

    private static final int discountTwoForThree = 3;
    private static final String checkOutMessage = "%d.%d aws";
    private static final String checkOutMessageLessThanTenC = "%d.0%d aws";
    private List<String> cart;

    private Map<String, Integer> quantityOfProductsInCart;
    GroceriesShopAPI groceriesShop;

    public Customer(GroceriesShopAPI groceriesShop) {
        cart = new ArrayList<>();
        this.groceriesShop = groceriesShop;
        this.quantityOfProductsInCart = new HashMap<>();
    }

    @Override
    public void addToCart(String productName, int quantity) {
        if (productName == null || productName.isEmpty() || quantity <= 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (groceriesShop.getProducts().containsKey(productName)) {
            for (int i = 0; i < quantity; i++) {
                cart.add(productName);
            }
            if (quantityOfProductsInCart.containsKey(productName)) {
                quantityOfProductsInCart.put(productName, quantityOfProductsInCart.get(productName) + quantity);
            } else {
                quantityOfProductsInCart.put(productName, quantity);
            }
        } else {
            throw new InvalidProductException("Product not found");
        }
    }

    @Override
    public void removeFromCart(String productName, int quantity) {
        if (productName == null || productName.isEmpty() || quantity < 0) {
            throw new IllegalArgumentException();
        }

        if (groceriesShop.getProducts().containsKey(productName)) {
            int count = 0;

            while (count < quantity) {
                if (cart.remove(productName)) {
                    count++;
                    quantityOfProductsInCart.put(productName, quantityOfProductsInCart.get(productName) - 1);
                    if (quantityOfProductsInCart.get(productName) == 0) {
                        quantityOfProductsInCart.remove(productName);
                    }
                } else {
                    break;
                }
            }

        } else {
            throw new InvalidProductException("Product not found");
        }

    }

    @Override
    public String checkout() {
        int total = 0;
        total += filterBuyOneGetHalfOffDiscounts();
        total += filterTwoForThreeDiscounts();
        for (String product : cart) {
            if (!groceriesShop.isInDiscount(product)) {
                total += groceriesShop.getProducts().get(product);
            }
        }
        cart.clear();
        quantityOfProductsInCart.clear();
        if(total % 100 < 10){
            return String.format(checkOutMessageLessThanTenC, total / 100, total % 100);
        }
        return String.format(checkOutMessage, total / 100, total % 100);
    }

    private int filterTwoForThreeDiscounts() {
        int total = 0;

        for (Set<String> discount : groceriesShop.getDiscountsTwoForThree()) {
            int count = 0;
            int lowestPrice = Integer.MAX_VALUE;
            int priceToAdd = 0;
            for (String name : cart) {
                if (discount.contains(name)) {
                    int price = groceriesShop.getProducts().get(name);
                    priceToAdd += price;
                    if (price < lowestPrice) {
                        lowestPrice = price;
                    }
                    count++;

                    if (count == discountTwoForThree) {
                        total += priceToAdd - lowestPrice;
                        count = 0;
                        lowestPrice = Integer.MAX_VALUE;
                        priceToAdd = 0;
                    }
                }
            }
            total += priceToAdd;
        }

        return total;
    }

    private int filterBuyOneGetHalfOffDiscounts() {
        int total = 0;
        for (String productInDiscount : groceriesShop.getDiscountsBuyOneGetHalfOff()) {
            if (quantityOfProductsInCart.containsKey(productInDiscount)) {
                int quantityOfProduct = quantityOfProductsInCart.get(productInDiscount);
                int priceOfProduct = groceriesShop.getProducts().get(productInDiscount);
                total += (quantityOfProduct / 2) * (priceOfProduct / 2);
                if (quantityOfProduct % 2 == 0) {
                    total += (quantityOfProduct / 2) * priceOfProduct;
                } else {
                    total += ((quantityOfProduct / 2) + 1) * priceOfProduct;
                }
            }
        }
        return total;
    }

    public int getCartSize() {
        return cart.size();
    }

    @Override
    public void listCart() {
        for (String product : cart) {
            System.out.println(product + " for " + groceriesShop.getProducts().get(product) + " c.");
        }
    }
}
