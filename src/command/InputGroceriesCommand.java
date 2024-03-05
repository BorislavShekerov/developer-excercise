package command;

import database.ProductDataBase;
import product.Product;

public class InputGroceriesCommand implements Command {

    private static final int CLOUDS_IN_ONE_AWS = 100;
    private static final String AWS_CURRENCY_ABBREVIATION = "aws";
    private static final String CLOUDS_CURRENCY_ABBREVIATION = "c";
    private static final String GROCERIES_DELIMITER = ",";
    private static final String PRICE_NAME_DELIMITER = "-";
    private static final String PRICE_DELIMITER = " ";

    private ProductDataBase productDataBase;

    InputGroceriesCommand(ProductDataBase productDataBase) {
        this.productDataBase = productDataBase;
    }

    @Override
    public void execute(String arguments) {
        parseCommandArgs(arguments);
    }

    private void parseCommandArgs(String arguments) {
        String[] groceries = arguments.split(GROCERIES_DELIMITER);

        for (String grocery : groceries) {
            String[] parts = grocery.trim().split(PRICE_NAME_DELIMITER);

            if (parts.length == 2) {
                String productName = parts[0].trim();
                double productPrice;
                try {

                    productPrice = parsePrice(parts[1].trim());
                    productDataBase.addProduct(new Product(productName, productPrice));

                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format for product: " + productName);
                }
            }
        }
    }

    private double parsePrice(String priceString) {
        String[] parts = priceString.split(PRICE_DELIMITER);

        if (parts.length == 1) {
            if (parts[0].contains(AWS_CURRENCY_ABBREVIATION)) {
                return getNumberFromString(parts[0], AWS_CURRENCY_ABBREVIATION);
            } else if (parts[0].contains(CLOUDS_CURRENCY_ABBREVIATION)) {
                return getNumberFromString(parts[0], CLOUDS_CURRENCY_ABBREVIATION);
            } else {
                throw new NumberFormatException("Invalid price format");
            }
        } else if (parts.length == 2) {
            double aws = getNumberFromString(parts[0], AWS_CURRENCY_ABBREVIATION);
            double clouds = getNumberFromString(parts[1], CLOUDS_CURRENCY_ABBREVIATION);

            return aws * CLOUDS_IN_ONE_AWS + clouds;
        }

        throw new NumberFormatException("Invalid price format: " + priceString);
    }

    private double getNumberFromString(String priceString, String currency) {
        if (priceString.contains(currency)) {
            String awsString = priceString.replace(currency, "");
            double aws = Double.parseDouble(awsString);

            return aws;
        }
        throw new NumberFormatException("Invalid price format" + priceString);
    }
}