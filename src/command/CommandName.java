package command;

public enum CommandName {
    INPUT_GROCERIES("input-groceries"),
    INPUT_DEALS("input-deals"),
    SHOW_INTERFACE("show-interface"),
    CUSTOMER_BASKET("customer-basket");

    public final String userCommand;

    CommandName(String userCommand) {
        this.userCommand = userCommand;
    }
}
