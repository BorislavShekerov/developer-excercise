package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientBasket {

    private List<Product> products;

    public ClientBasket() {
        this.products = new ArrayList<>();
    }

    public void addToTheBasket(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }


}
