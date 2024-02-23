package service;

import entity.FantasticoGroceryShop;
import entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.List;


public class FantasticoGroceryShopServiceTest {

    private FantasticoGroceryShopService service;

    private FantasticoGroceryShop shop;

    private final String inputProduct = "apple-50,banana-40";

    @Rule
    public final TextFromStandardInputStream systemIn = TextFromStandardInputStream.emptyStandardInputStream();

    @Before
    public void setUp() {
        service =
                new FantasticoGroceryShopService();
        shop = FantasticoGroceryShop.fantasticoShop();
    }


    @Test
    public void insertProductsShouldReturnCorrectProducts() {
        int expected = 2;
            systemIn.provideLines(inputProduct + "\n" +
                    "Yes \n" +
                    "Two for three \n" +
                    "No");
        List<Product> products = service.insertProducts();
        int actual = products.size();
        Assert.assertEquals(expected, actual);
        for (Product product : products) {
            Assert.assertTrue(inputProduct.contains(product.getName()));
        }
        shop.reset();
    }

    @Test
    public void insertProductsShouldReturnCorrectNumberTwoForThreePromo() {
        int expected = 2;
        systemIn.provideLines(inputProduct + "\n" +
                "yes \n" +
                "two for three \n" +
                "yes \n" +
                "two for three \n");
        service.insertProducts();
        int actual = shop.getTwoForThreePromotionItems().size();
        Assert.assertEquals(expected, actual);
        shop.reset();
    }

    @Test
    public void insertProductsShouldReturnZeroTwoForThreePromo() {
        int expected = 0;
        systemIn.provideLines(inputProduct + "\n" +
                "no \n" +
                "no" );
        service.insertProducts();
        int actual = shop.getTwoForThreePromotionItems().size();
        Assert.assertEquals(expected, actual);
        shop.reset();
    }

    @Test
    public void insertProductsShouldReturnCorrectNumberHalfPricePromo() {
        int expected = 2;
        systemIn.provideLines(inputProduct + "\n" +
                "yes \n" +
                "half price \n" +
                "yes \n" +
                "half price \n");
        service.insertProducts();
        int actual = shop.getHalfOfAPriceItems().size();
        Assert.assertEquals(expected, actual);
        shop.reset();
    }

    @Test
    public void insertProductsShouldReturnZeroNumberHalfPricePromo() {
        int expected = 0;
        systemIn.provideLines(inputProduct + "\n" +
                "no \n" +
                "no" );
        service.insertProducts();
        int actual = shop.getHalfOfAPriceItems().size();
        Assert.assertEquals(expected, actual);
        shop.reset();
    }

    @Test
    public void insertProductsShouldReturnZeroNumberHalfPricePromoAndHalfPricePromoIfInputNotCorrect() {
        int expected = 0;
        systemIn.provideLines(inputProduct + "\n" +
                "yes \n" +
                "two for thre \n" +
                "yes \n" +
                "half price2 \n");
        service.insertProducts();
        int actualHalfPrice = shop.getHalfOfAPriceItems().size();
        int actualTwoForThree = shop.getTwoForThreePromotionItems().size();
        Assert.assertEquals(expected, actualHalfPrice);
        Assert.assertEquals(expected, actualTwoForThree);
        shop.reset();
    }


}
