package groceryshop;

import command.CommandExecutor;
import database.ProductDataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GroceryShopTest {

    @InjectMocks
    private GroceryShop groceryShop;

    @Mock
    private ProductDataBase productDataBase;

    @Mock
    private CommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void runExecuteCommandTest() {
        // Given
        String input = "show-interface" + System.lineSeparator() + "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        groceryShop.run();

        verify(commandExecutor, times(1)).executeCommand("show-interface");
    }

    @Test
    public void runQuitCommandTest() {
        String input = "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        groceryShop.run();

        verify(commandExecutor, never()).executeCommand(anyString());
    }

}