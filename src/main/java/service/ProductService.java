package service;

import entity.message.Messages;
import entity.Product;

public class ProductService {

    public Product administrateProduct (String productNameAndPrice) {
        String[] productAndPrice = productNameAndPrice.trim().split("\\s*-\\s*");
        if(productAndPrice.length != 2) {
            throw new IllegalArgumentException(Messages.WRONG_INPUT);
        }
        String productName = productAndPrice[0];
        Double price;
        try {
            price = Double.valueOf(productAndPrice[1]);
            return new Product(productName, price);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(Messages.PRICE_MUST_BE_NUMBER);
        }
    }




}
