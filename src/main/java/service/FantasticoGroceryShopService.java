package service;

import entity.ClientBasket;
import entity.FantasticoGroceryShop;
import entity.message.Messages;
import entity.Product;
import entity.enums.PromotionEnum;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class FantasticoGroceryShopService implements ShopService {

    private FantasticoGroceryShop fantasticoGroceryShop;

    private ProductService productService;

    private final Scanner scanner = new Scanner(System.in);

    public FantasticoGroceryShopService() {
        fantasticoGroceryShop = FantasticoGroceryShop.fantasticoShop();
        productService = new ProductService();
    }

    private void addTwoForThreePromotionItem(Product product) {
        this.fantasticoGroceryShop.getTwoForThreePromotionItems().add(product);
    }

    private void addHalfPricePromotion(Product product) {
        this.fantasticoGroceryShop.getHalfOfAPriceItems().add(product);
    }

    public List<Product> insertProducts() {
        System.out.println(Messages.INSERT_PRODUCT);
        String[] productsSeparatedByComma = scanner.nextLine().split("\\s*,\\s*");

        for (String productAndPrice : productsSeparatedByComma) {
            Product product = this.productService.administrateProduct(productAndPrice.trim());
            this.fantasticoGroceryShop.getProducts().add(product);
            System.out.printf(Messages.ADD_PROMOTION + "\n", product.getName());
            boolean doesAdministratorWantToAddItemToPromotions = scanner.nextLine().trim().equalsIgnoreCase("Yes");

            if (doesAdministratorWantToAddItemToPromotions) {
                this.addPromotionForProduct(product);
            }
        }
        System.out.println(Messages.READY_FOR_SHOPPING);
        return this.fantasticoGroceryShop.getProducts();
    }

    private void addPromotionForProduct(Product product) {
        System.out.println(Messages.CHOOSE_PROMOTION);
        String administratorMessage = scanner.nextLine().trim();
        String nameOfPromotion = "";

        if (administratorMessage.equalsIgnoreCase("Half price")) {
            nameOfPromotion = PromotionEnum.HALF_PRICE.name();
        } else if (administratorMessage.equalsIgnoreCase("Two for three")) {
            nameOfPromotion = PromotionEnum.TWO_FOR_THREE.name();
        } else {
            nameOfPromotion = "nothing";
        }

        addPromotionalItem(product, nameOfPromotion);
    }

    private void addPromotionalItem(Product product, String nameOfPromotion) {
        if (nameOfPromotion.equalsIgnoreCase(PromotionEnum.HALF_PRICE.name())) {
            this.addHalfPricePromotion(product);
        } else if (nameOfPromotion.equalsIgnoreCase(PromotionEnum.TWO_FOR_THREE.name())) {
            this.addTwoForThreePromotionItem(product);
        } else {
            System.out.println(Messages.WRONG_PROMOTION);
        }
    }

    public void shop(ClientBasket clientBasket) {
        System.out.println(Messages.AVAILABLE_PRODUCTS);
        this.fantasticoGroceryShop.getProducts()
                .forEach(product -> System.out.println("Product: " + product.getName() + " - price: " + product.getPrice()));
        chooseItemsToBuy(clientBasket);
    }

    private void chooseItemsToBuy(ClientBasket clientBasket) {
        System.out.println(Messages.INSERT_PRODUCTS_SEPARATED_BY_COMMA);
        String[] nameOfProducts = scanner.nextLine().trim().split("\\s*,\\s*");


        for (String productName : nameOfProducts) {
            Product clientProduct = this.fantasticoGroceryShop.getProducts().stream()
                    .filter(product -> product.getName().equalsIgnoreCase(productName))
                    .findFirst()
                    .orElse(null);

            if (clientProduct == null) {
                System.out.printf(Messages.PRODUCT_DOES_NOT_EXIST + "\n", productName);
            } else {
                clientBasket.addToTheBasket(clientProduct);
            }
        }
        scanTheBill(clientBasket.getProducts());
    }


    private void scanTheBill(List<Product> clientProducts) {
        List<Product> twoForThreeProducts = new ArrayList<>();
        Set<Product> halfPriceProducts = new HashSet<>();
        CurrencyExchange exchange = new CurrencyExchangeService();
        StringBuilder builder = new StringBuilder();
        double billOfClientInClouds = 0;
        int productNumber = 1;

        for (Product clientProduct : clientProducts) {
            String nameOfProduct = clientProduct.getName();
            Double priceOfProduct = clientProduct.getPrice();
            builder.append("Product: ").append(nameOfProduct).append(" with number: ").append(productNumber)
                    .append(" - price: ").append(priceOfProduct).append(System.lineSeparator());
            boolean isHalfItemPromoJustRemoved = false;

            if (!halfPriceProducts.isEmpty()) {
                boolean isThisSecondItemOfTheSameProduct = halfPriceProducts.stream()
                        .anyMatch(product -> product.getName().equalsIgnoreCase(nameOfProduct));

                if (isThisSecondItemOfTheSameProduct) {
                    priceOfProduct = priceOfProduct / 2;
                    builder.append("Product: ").append(nameOfProduct).append(" with number: ").append(productNumber)
                            .append(Messages.ON_HALF_PRICE).append(priceOfProduct)
                            .append(System.lineSeparator());
                    halfPriceProducts.removeIf(product -> product.getName().equalsIgnoreCase(nameOfProduct));
                    isHalfItemPromoJustRemoved = true;
                }
            }

            if (isProductOnHalfPricePromo(nameOfProduct) && !isHalfItemPromoJustRemoved) {
                halfPriceProducts.add(clientProduct);
            }

            if (isProductOnTwoForThreePromo(nameOfProduct)) {
                twoForThreeProducts.add(clientProduct);
            }
            if (twoForThreeProducts.size() == 3) {
                Double theCheapestProduct = findTheCheapestProduct(twoForThreeProducts);

                builder.append("Product: ").append(nameOfProduct)
                        .append(Messages.TWO_FOR_THREE).append(theCheapestProduct)
                        .append(System.lineSeparator());

                billOfClientInClouds -= theCheapestProduct;
                twoForThreeProducts.clear();
            }
            productNumber++;
            billOfClientInClouds += priceOfProduct;
        }
        printBill(exchange.exchangeCloudsToAws(billOfClientInClouds), builder.toString());
    }


    private Double findTheCheapestProduct(List<Product> twoForThreeProducts) {
        return twoForThreeProducts.stream()
                .map(Product::getPrice).min(Double::compareTo).get();
    }

    private void printBill(BigDecimal aws, String bill) {
        System.out.println(bill);
        String awsAndClouds = aws.toString();
        int indexOfClouds = awsAndClouds.indexOf(".");
        if (indexOfClouds == -1) {
            System.out.printf("The bill is %s aws\n", awsAndClouds);
        } else {
            String awsInString = awsAndClouds.substring(0, indexOfClouds);
            String clouds = awsAndClouds.substring(indexOfClouds + 1);
            if (awsInString.equals("0")) {
                System.out.printf("Total amount: %s clouds\n", clouds);
            } else {
                System.out.printf("Total amount: %s aws and %s clouds\n", awsInString, clouds);
            }
        }
    }

    private boolean isProductOnTwoForThreePromo(String nameOfProduct) {
        return this.fantasticoGroceryShop.getTwoForThreePromotionItems().stream()
                .anyMatch(product -> product.getName()
                        .equalsIgnoreCase(nameOfProduct));
    }

    private boolean isProductOnHalfPricePromo(String nameOfProduct) {
        return this.fantasticoGroceryShop.getHalfOfAPriceItems().stream()
                .anyMatch(product -> product.getName()
                        .equalsIgnoreCase(nameOfProduct));
    }

}
