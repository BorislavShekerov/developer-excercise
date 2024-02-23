package service;

import entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductServiceTest {

    private ProductService productService;

    private final String VALID = "apple-50";
    private final String NOT_VALID = "apple50";
    private final String NOT_PRICE = "apple-ha";

    @Before
    public void setUp() {
        productService = new ProductService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void administrateProductShouldThrowIfNotOnlyNameAndPrice() {
        productService.administrateProduct(NOT_VALID);
    }

    @Test(expected = NumberFormatException.class)
    public void administrateProductShouldThrowIfSecondElementNotNumber() {
        productService.administrateProduct(NOT_PRICE);
    }

    @Test
    public void administrateProductShouldReturnCorrectProduct() {
        Product product = productService.administrateProduct(VALID);
        Assert.assertTrue(VALID.contains(product.getName()));
    }

}
