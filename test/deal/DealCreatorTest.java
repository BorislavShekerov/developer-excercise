package deal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deal.BuyOneGetOneHalfPriceDeal;
import deal.Deal;
import deal.DealCreator;
import deal.DealName;
import deal.TwoForThreeDeal;
import product.Product;

public class DealCreatorTest {

    private DealCreator dealCreator;

    @BeforeEach
    public void setUp() {
        dealCreator = new DealCreator();
    }

    @Test
    public void testCreateDealWithBuyOneGetOneHalfPrice() {
        // Arrange
        List<Product> products = new ArrayList<>();
        DealName dealName = DealName.BUY_ONE_GET_ONE_HALF_PRICE;

        // Act
        Deal deal = dealCreator.createDeal(dealName, products);

        // Assert
        assertNotNull(deal);
        assertTrue(deal instanceof BuyOneGetOneHalfPriceDeal);
    }

    @Test
    public void testCreateDealWithTwoForThreeDeal() {
        // Arrange
        List<Product> products = new ArrayList<>();
        DealName dealName = DealName.TWO_FOR_THREE_DEAL;

        // Act
        Deal deal = dealCreator.createDeal(dealName, products);

        // Assert
        assertNotNull(deal);
        assertTrue(deal instanceof TwoForThreeDeal);
    }

    @Test
    public void testCreateDealWithNullDealName() {
        // Arrange
        List<Product> products = new ArrayList<>();
        DealName dealName = null;

        // Act
        Deal deal = dealCreator.createDeal(dealName, products);

        // Assert
        assertNull(deal);
    }
}
