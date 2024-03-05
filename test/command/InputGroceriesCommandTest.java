package command;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import command.InputGroceriesCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import database.ProductDataBase;
import product.Product;

public class InputGroceriesCommandTest {

    private InputGroceriesCommand inputGroceriesCommand;
    private ProductDataBase productDataBase;

    @BeforeEach
    public void setUp() {
        productDataBase = mock(ProductDataBase.class);
        inputGroceriesCommand = new InputGroceriesCommand(productDataBase);
    }

    @Test
    public void testExecuteWithValidArguments() {
        // Arrange
        String arguments = "apple-1aws 50c, banana-40c, tomato-30c, potato-26c";

        // Act
        inputGroceriesCommand.execute(arguments);

        // Assert
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDataBase, times(4)).addProduct(productCaptor.capture());

        // Verify each product individually
        assertProduct("apple", 150.0, productCaptor.getAllValues().get(0));
        assertProduct("banana", 40.0, productCaptor.getAllValues().get(1));
        assertProduct("tomato", 30.0, productCaptor.getAllValues().get(2));
        assertProduct("potato", 26.0, productCaptor.getAllValues().get(3));
    }



    private void assertProduct(String name, double price, Product product) {
        assertEquals(name, product.name());
        assertEquals(price, product.price());
    }



    // Add more test cases as needed...
}
