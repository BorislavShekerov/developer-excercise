package entity;

import service.ProductService;

import java.util.*;

public class FantasticoGroceryShop{

    private static FantasticoGroceryShop shop;
    private List<Product> products;
    private List<Product> twoForThreePromotionItems;
    private List<Product> halfOfAPriceItems;

    private FantasticoGroceryShop() {
        products = new ArrayList<>();
        twoForThreePromotionItems = new ArrayList<>();
        halfOfAPriceItems = new ArrayList<>();
    }

    public static FantasticoGroceryShop fantasticoShop() {
        if (shop == null) {
            shop = new FantasticoGroceryShop();
        }
        return shop;
    }

    public List<Product> getProducts() {
        return products;
    }


    public List<Product> getTwoForThreePromotionItems() {
        return twoForThreePromotionItems;
    }


    public List<Product> getHalfOfAPriceItems() {
        return halfOfAPriceItems;
    }

    public void reset() {
        shop = null;
    }


}
