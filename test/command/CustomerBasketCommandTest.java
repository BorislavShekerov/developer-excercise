package command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.ProductDataBase;
import deal.Deal;
import product.Product;

public class CustomerBasketCommandTest {

    private CustomerBasketCommand customerBasketCommand;
    private ProductDataBase productDataBase;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        productDataBase = mock(ProductDataBase.class);
        customerBasketCommand = new CustomerBasketCommand(productDataBase);
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void executeWithValidProductsNoDealsAppliedTest() {
        // Arrange
        String arguments = "apple, banana, tomato";
        List<Product> products = new ArrayList<>();
        products.add(new Product("apple", 50));
        products.add(new Product("banana", 40));
        products.add(new Product("tomato", 30));

        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(new Product("apple", 50))
            .thenReturn(new Product("banana", 40))
            .thenReturn(new Product("tomato", 30));
        when(productDataBase.getDeals()).thenReturn(new ArrayList<>()); // No deals applied

        customerBasketCommand.execute(arguments);

        assertEquals("The total is: 1aws 20c" + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void executeWithValidProductsDealAppliedTest() {
        String arguments = "apple, banana, tomato";
        List<Product> products = new ArrayList<>();
        products.add(new Product("apple", 50));
        products.add(new Product("banana", 40));
        products.add(new Product("tomato", 30));

        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(new Product("apple", 50))
            .thenReturn(new Product("banana", 40))
            .thenReturn(new Product("tomato", 30));

        Deal mockDeal = mock(Deal.class);
        when(mockDeal.qualifiesForDeal(any())).thenReturn(true);
        when(mockDeal.calculateDiscount(any())).thenReturn(10.0); // Mock discount
        List<Deal> deals = new ArrayList<>();
        deals.add(mockDeal);
        when(productDataBase.getDeals()).thenReturn(deals);

        customerBasketCommand.execute(arguments);

        assertEquals("The total is: 1aws 10c" + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void testExecute_WithNoSuchProduct() {
        String arguments = "apple, banana, tomato";
        List<Product> products = new ArrayList<>();
        products.add(new Product("apple", 50));
        products.add(new Product("banana", 40));

        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(new Product("apple", 50))
            .thenReturn(new Product("banana", 40))
            .thenReturn(null); // Mocking one product not found

        customerBasketCommand.execute(arguments);

        assertEquals("Unknown products in the basket. Can't calculate the basket total" + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    public void executeWithPriceOnlyInCloudsTest() {
        String arguments = "apple, tomato";
        List<Product> products = new ArrayList<>();
        products.add(new Product("apple", 50));
        products.add(new Product("tomato", 30));

        when(productDataBase.searchProductByName(anyString()))
            .thenReturn(new Product("apple", 50))
            .thenReturn(new Product("tomato", 30));
        when(productDataBase.getDeals()).thenReturn(new ArrayList<>()); // No deals applied

        customerBasketCommand.execute(arguments);

        assertEquals("The total is: 80c" + System.lineSeparator(), outputStreamCaptor.toString());
    }

}
