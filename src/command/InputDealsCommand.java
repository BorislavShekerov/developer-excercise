package command;

import database.ProductDataBase;
import deal.Deal;
import deal.DealCreator;
import deal.DealName;
import product.Product;

import java.util.ArrayList;
import java.util.List;

public class InputDealsCommand implements Command {

    private static final String DEALS_DELIMITER = ";";
    private static final String DEAL_PARTS_DELIMITER = "-";
    private static final String PRODUCT_NAMES_DELIMITER = ",";
    private ProductDataBase productDataBase;
    private DealCreator dealCreator;

    public InputDealsCommand(ProductDataBase productDataBase) {
        this.productDataBase = productDataBase;
        this.dealCreator = new DealCreator();
    }

    public void setDealCreator(DealCreator dealCreator) {
        this.dealCreator = dealCreator;
    }

    @Override
    public void execute(String arguments) {
        parseCommandArgs(arguments);
    }

    private void parseCommandArgs(String arguments) {
        String[] deals = arguments.split(DEALS_DELIMITER);
        for (String deal : deals) {
            String[] dealParts = deal.split(DEAL_PARTS_DELIMITER);
            if (dealParts.length == 2) {
                DealName dealName = findDealByName(dealParts[0].trim());
                List<Product> dealProducts = findProducts(dealParts[1]);

                if (!validateDeal(dealName, dealProducts)) {
                    continue;
                }

                Deal currentDeal = dealCreator.createDeal(dealName, dealProducts);
                productDataBase.addDeal(currentDeal);
            } else {
                System.out.println("Invalid deal template");
            }
        }
    }

    private boolean validateDeal(DealName dealName, List<Product> dealProducts) {

        if (dealName == null) {
            System.out.println("Deal is not currently supported. This deal will not be created");
            return false;
        }
        if (dealProducts == null) {
            System.out.println("Problem with finding given products. Deal will not be created");
            return false;
        }

        return true;
    }

    private DealName findDealByName(String dealPart) {
        for (DealName currentDealName : DealName.values()) {
            if (dealPart.equals(currentDealName.dealName)) {
                return currentDealName;
            }
        }

        return null;
    }

    private List<Product> findProducts(String productPart) {
        String[] productNames = productPart.split(PRODUCT_NAMES_DELIMITER);
        if (productNames.length == 0) {
            System.out.println("No products given");
            return null;
        }
        List<Product> toReturn = new ArrayList<>();

        for (String currentProductName : productNames) {
            Product tempProduct = productDataBase.searchProductByName(currentProductName.trim());
            if (tempProduct == null) {
                System.out.println("No such product. " + currentProductName);
                return null;
            }

            toReturn.add(tempProduct);
        }

        return toReturn;
    }
}
