package groceryshop;

import command.CommandExecutor;
import command.ShowInterfaceCommand;
import database.ProductDataBase;

import java.util.Scanner;

public class GroceryShop {
    private static final String WELCOME_MESSAGE =
        "Welcome to the Grocery Shop till. Enter one of the following commands";
    private ProductDataBase productDataBase;
    private CommandExecutor commandExecutor;

    public GroceryShop() {
        this.productDataBase = new ProductDataBase();
        this.commandExecutor = new CommandExecutor(productDataBase);
    }

    public void run() {
        ShowInterfaceCommand interfaceCommand = new ShowInterfaceCommand();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(WELCOME_MESSAGE);
            interfaceCommand.execute("");
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if ("quit".equalsIgnoreCase(message.trim())) {
                    break;
                }

                commandExecutor.executeCommand(message);
            }
        }
    }
}
