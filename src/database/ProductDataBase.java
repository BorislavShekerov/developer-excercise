package database;

import deal.Deal;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDataBase {
    private List<Product> productsList;
    private Map<String, Product> products;
    private List<Deal> deals;

    public ProductDataBase() {
        this.productsList = new ArrayList<>();
        this.products = new HashMap<>();
        this.deals = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (products.get(product.name()) != null) {
            System.out.println("Product" + product.name() + " is already in. The product price will not be updated");
            return;
        }
        productsList.add(product);
        products.put(product.name(), product);
    }

    public void addDeal(Deal deal) {
        deals.add(deal);
    }

    public Product searchProductByName(String productName) {
        return products.get(productName);
    }

    public List<Deal> getDeals() {
        return deals;
    }

}
