package entity;

import java.util.Objects;

public class Product {

    private String name;

    private Double priceCloud;

    public Product(String name, Double price) {
        this.name = name;
        this.priceCloud = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return priceCloud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(priceCloud, product.priceCloud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priceCloud);
    }


}
