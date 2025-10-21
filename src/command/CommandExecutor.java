package command;

import database.ProductDataBase;

import java.util.EnumMap;

public class CommandExecutor {

    private static final int COMMAND_NAME_INDEX = 0;
    private static final int COMMAND_ARGUMENTS_INDEX = 1;

    private ProductDataBase productDataBase;
    private EnumMap<CommandName, Command> commandMap;

    public CommandExecutor(ProductDataBase productDataBase) {
        this.productDataBase = productDataBase;

        commandMap = new EnumMap<>(CommandName.class);
        commandMap.put(CommandName.INPUT_GROCERIES, new InputGroceriesCommand(productDataBase));
        commandMap.put(CommandName.INPUT_DEALS, new InputDealsCommand(productDataBase));
        commandMap.put(CommandName.CUSTOMER_BASKET, new CustomerBasketCommand(productDataBase));
        commandMap.put(CommandName.SHOW_INTERFACE, new ShowInterfaceCommand());
    }

    public void executeCommand(String userInput) {

        String[] command = userInput.split(":");

        if (command.length > 2) {
            System.out.println("Invalid command template");
            return;
        }

        CommandName commandName = getCommandName(command[COMMAND_NAME_INDEX]);

        if (commandName != null) {
            if (commandName.equals(CommandName.SHOW_INTERFACE)) {
                commandMap.get(commandName).execute(null);
                return;
            }

            commandMap.get(commandName).execute(command[COMMAND_ARGUMENTS_INDEX]);
        } else {
            System.out.println("Unknown command template");
        }
    }

    CommandName getCommandName(String userCommand) {
        for (CommandName commandName : CommandName.values()) {
            if (userCommand.equals(commandName.userCommand)) {
                return commandName;
            }
        }

        return null;
    }

}
