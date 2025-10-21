package command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.ProductDataBase;
import deal.Deal;
import deal.DealCreator;
import deal.DealName;
import product.Product;

import java.util.List;

public class InputDealsCommandTest {

    private InputDealsCommand inputDealsCommand;
    private ProductDataBase productDataBase;
    private DealCreator dealCreator;

    @BeforeEach
    public void setUp() {
        productDataBase = mock(ProductDataBase.class);
        dealCreator = mock(DealCreator.class);
        inputDealsCommand = new InputDealsCommand(productDataBase);
        inputDealsCommand.setDealCreator(dealCreator);
    }

    @Test
    public void executeWithValidArgumentsTest() {
        String arguments = "two for three - apple, banana, tomato";

        DealName expectedDealName = DealName.TWO_FOR_THREE_DEAL;
        Product expectedProduct1 = new Product("apple", 50);
        Product expectedProduct2 = new Product("banana", 40);
        Product expectedProduct3 = new Product("tomato", 30);
        List<Product> expectedProducts = List.of(expectedProduct1, expectedProduct2, expectedProduct3);
        Deal expectedDeal = mock(Deal.class);

        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(new Product("apple", 50))
            .thenReturn(new Product("banana", 40))
            .thenReturn(new Product("tomato", 30));
        when(dealCreator.createDeal(expectedDealName, expectedProducts)).thenReturn(expectedDeal);

        inputDealsCommand.execute(arguments);

        verify(productDataBase).addDeal(expectedDeal);
    }

    @Test
    public void testExecute_WithInvalidDealName() {
        String arguments = "invalid deal - apple, banana, tomato";

        inputDealsCommand.execute(arguments);

        verify(productDataBase, never()).addDeal(any());
    }

    @Test
    public void testExecute_WithInvalidProducts() {
        String arguments = "two for three - apple, banana, invalid_product";

        when(productDataBase.searchProductByName("invalid_product")).thenReturn(null);

        inputDealsCommand.execute(arguments);
        verify(productDataBase, never()).addDeal(any());
    }

    @Test
    public void nullDealTest() {
        String arguments = "invalid deal: tomato";
        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(new Product("tomato", 30));

        inputDealsCommand.execute(arguments);

        verify(productDataBase, never()).addDeal(any());
    }

    @Test
    public void nullProductTest() {
        String arguments = "input-groceries: milk";
        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(null);

        inputDealsCommand.execute(arguments);

        verify(productDataBase, never()).addDeal(any());
    }

    @Test
    public void noProductsTest() {
        String arguments = "input-groceries: ";

        inputDealsCommand.execute(arguments);

        verify(productDataBase, never()).addDeal(any());
    }

}
