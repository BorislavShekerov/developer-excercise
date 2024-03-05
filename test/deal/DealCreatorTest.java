package deal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import product.Product;

public class DealCreatorTest {

    private DealCreator dealCreator;

    @BeforeEach
    public void setUp() {
        dealCreator = new DealCreator();
    }

    @Test
    public void testCreateDealWithBuyOneGetOneHalfPrice() {
        List<Product> products = new ArrayList<>();
        DealName dealName = DealName.BUY_ONE_GET_ONE_HALF_PRICE;

        Deal deal = dealCreator.createDeal(dealName, products);

        assertNotNull(deal);
        assertTrue(deal instanceof BuyOneGetOneHalfPriceDeal);
    }

    @Test
    public void testCreateDealWithTwoForThreeDeal() {
        List<Product> products = new ArrayList<>();
        DealName dealName = DealName.TWO_FOR_THREE_DEAL;

        Deal deal = dealCreator.createDeal(dealName, products);

        assertNotNull(deal);
        assertTrue(deal instanceof TwoForThreeDeal);
    }

    @Test
    public void testCreateDealWithNullDealName() {
        List<Product> products = new ArrayList<>();
        DealName dealName = null;

        Deal deal = dealCreator.createDeal(dealName, products);

        assertNull(deal);
    }
}
