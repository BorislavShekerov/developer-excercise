package entity;

import entity.message.Messages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import service.FantasticoGroceryShopService;

import java.math.BigDecimal;
import java.util.List;

public class FantasticoClientTest {

    private FantasticoClient fantasticoClient;

    private FantasticoGroceryShopService service;

    private final String inputProduct = "apple-50,banana-26";

    private FantasticoGroceryShop shop;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemIn = TextFromStandardInputStream.emptyStandardInputStream();


    @Before
    public void setUp() {
        fantasticoClient = new FantasticoClient();
        service = new FantasticoGroceryShopService();
        shop = FantasticoGroceryShop.fantasticoShop();
    }

    public void productInsert() {
        systemIn.provideLines(inputProduct + "\n" +
                "Yes \n" +
                "Two for three \n" +
                "Yes \n" +
                "half price");
        service.insertProducts();
    }

    @Test
    public void shoppingAndChoosingItemsShouldReturnCorrectSum() {
        productInsert();
        BigDecimal applePrice = BigDecimal.valueOf(50);
        BigDecimal bananaPrice = BigDecimal.valueOf(26);
        BigDecimal sum = applePrice.add(bananaPrice).divide(BigDecimal.valueOf(100)).setScale(2);
        String sumInString = sum.toString();
        int indexBeforeAndAfterDecimal = sumInString.indexOf(".");
        String aws = sumInString.substring(0, indexBeforeAndAfterDecimal);
        String clouds = sumInString.substring(indexBeforeAndAfterDecimal + 1);
        systemIn.provideLines("banana,apple");
        fantasticoClient.shop(service);
        Assert.assertTrue(systemOutRule.getLog().contains(Messages.INSERT_PRODUCTS_SEPARATED_BY_COMMA));
        if(aws.equals("0")) {
            Assert.assertTrue(systemOutRule.getLog().contains(clouds + " clouds"));
        }else {
            Assert.assertTrue(systemOutRule.getLog().contains(aws + " aws and " + clouds + " clouds"));
        }
        shop.reset();
    }

    @Test
    public void shoppingAndChoosingItemsShouldReturnThatThereIsThreeForThreePromo() {
        productInsert();
        systemIn.provideLines("apple,apple,apple");
        fantasticoClient.shop(service);
        Assert.assertTrue(systemOutRule.getLog().contains(Messages.TWO_FOR_THREE));
        shop.reset();
    }

    @Test
    public void shoppingAndChoosingItemsShouldReturnThatThereIsHalfPricePromo() {
        productInsert();
        systemIn.provideLines("banana,banana");
        fantasticoClient.shop(service);
        Assert.assertTrue(systemOutRule.getLog().contains(Messages.ON_HALF_PRICE));
        shop.reset();
    }

    @Test
    public void shoppingAndChoosingItemsShouldReturnNoSuchProduct() {
        productInsert();
        String nameOfItem = "strawberry";
        systemIn.provideLines(nameOfItem,nameOfItem);
        fantasticoClient.shop(service);
        String expected = nameOfItem + " does not exist";
        Assert.assertTrue(systemOutRule.getLog().contains(expected));
        shop.reset();
    }



}
