package command;

import database.ProductDataBase;
import deal.Deal;
import product.Product;

import java.util.ArrayList;
import java.util.List;

public class CustomerBasketCommand implements Command {

    private static final int CLOUDS_IN_ONE_AWS = 100;
    private static final String GROCERIES_DELIMITER = ",";
    private ProductDataBase productDataBase;

    public CustomerBasketCommand(ProductDataBase productDataBase) {
        this.productDataBase = productDataBase;
    }

    @Override
    public void execute(String arguments) {
        List<Product> products = getProductsFromString(arguments);

        if (products == null) {
            System.out.println("Unknown products in the basket. Can't calculate the basket total");
            return;
        }

        double price = calculateDiscounts(products);

        int aws = priceToAws(price);
        int clouds = (int) price - aws * CLOUDS_IN_ONE_AWS;

        if (aws > 0) {
            System.out.println("The total is: " + aws + "aws " + clouds + "c");
        } else {
            System.out.println("The total is: " + clouds + "c");
        }
    }

    private List<Product> getProductsFromString(String arguments) {
        List<Product> toReturn = new ArrayList<>();
        String[] productsArray = arguments.split(GROCERIES_DELIMITER);

        for (String currentProductString : productsArray) {
            Product tempProduct = productDataBase.searchProductByName(currentProductString.trim());
            if (tempProduct == null) {
                return null;
            } else {
                toReturn.add(tempProduct);
            }
        }

        return toReturn;
    }

    private double calculateDiscounts(List<Product> products) {
        List<Deal> deals = productDataBase.getDeals();
        double totalCost = calculateTotalCost(products);

        for (Deal currentDeal : deals) {
            if (currentDeal.qualifiesForDeal(products)) {
                totalCost -= currentDeal.calculateDiscount(products);
            }
        }

        return totalCost;
    }

    private double calculateTotalCost(List<Product> products) {
        double price = 0;
        for (Product currentProduct : products) {
            price += currentProduct.price();
        }

        return price;
    }

    private int priceToAws(double price) {
        return (int) (price / CLOUDS_IN_ONE_AWS);
    }
}
