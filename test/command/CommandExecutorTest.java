package command;

import database.ProductDataBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandExecutorTest {

    private CommandExecutor commandExecutor;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private ProductDataBase productDataBase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        commandExecutor = new CommandExecutor(productDataBase);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    String buildShowInterface() {
        StringBuilder tillInterface = new StringBuilder();
        tillInterface.append("Command interface for the Grocery shop till");
        tillInterface.append(System.lineSeparator());
        tillInterface.append("1. input-groceries command");
        tillInterface.append(System.lineSeparator());
        tillInterface.append("  Example1: input-groceries: apple-50c, banana-40c, tomato-30c, potato-26c");
        tillInterface.append(System.lineSeparator());
        tillInterface.append("  Example2: input-groceries: apple-50c, banana-40c, tomato-1aws 60c, potato-2aws 50c");
        tillInterface.append(System.lineSeparator());
        tillInterface.append(System.lineSeparator());
        tillInterface.append("2. input-deals command");
        tillInterface.append(System.lineSeparator());
        tillInterface.append("  Example: input-deals: two for three - apple, banana, tomato; buy 1 get 1 half price - potato");
        tillInterface.append(System.lineSeparator());
        tillInterface.append(System.lineSeparator());
        tillInterface.append("3. customer-basket command");
        tillInterface.append(System.lineSeparator());
        tillInterface.append("  Example: customer-basket: apple, banana, banana, potato, tomato, banana, potato");
        tillInterface.append(System.lineSeparator());
        tillInterface.append(System.lineSeparator());
        tillInterface.append("4. show-interface:");
        tillInterface.append(System.lineSeparator());
        tillInterface.append("5. quit:");
        tillInterface.append(System.lineSeparator());

        return tillInterface.toString();
    }

    @Test
    public void executeCommandWithInvalidCommandTemplateTest() {
        String userInput = "input-groceries: apple-50c, banana-40c: potato-26c";

        commandExecutor.executeCommand(userInput);

        String expectedOutput = "Invalid command template";
        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    public void executeCommandWithUnknownCommandTest() {
        String userInput = "unknown command: apple-50c, banana-40c";

        commandExecutor.executeCommand(userInput);

        String expectedOutput = "Unknown command template";
        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    public void getCommandNameWithValidCommandsTest() {
        assertEquals(CommandName.INPUT_GROCERIES, commandExecutor.getCommandName("input-groceries"));
        assertEquals(CommandName.INPUT_DEALS, commandExecutor.getCommandName("input-deals"));
        assertEquals(CommandName.CUSTOMER_BASKET, commandExecutor.getCommandName("customer-basket"));
        assertEquals(CommandName.SHOW_INTERFACE, commandExecutor.getCommandName("show-interface"));
    }

    @Test
    public void getCommandNameWithInvalidCommandsTest() {
        assertNull(commandExecutor.getCommandName("invalid-command"));
        assertNull(commandExecutor.getCommandName(""));
    }

    @Test
    public void showInterfaceCommandTest() {
        String userInput = "show-interface:";

        commandExecutor.executeCommand(userInput);

        String expectedOutput = buildShowInterface();
        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }

    // Add more tests for other commands similarly...
}