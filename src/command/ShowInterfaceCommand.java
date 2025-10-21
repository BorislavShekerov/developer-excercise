package command;

public class ShowInterfaceCommand implements Command {
    @Override
    public void execute(String arguments) {
        System.out.println("Command interface for the Grocery shop till");
        System.out.println("1. input-groceries command");
        System.out.println("  Example1: input-groceries: apple-50c, banana-40c, tomato-30c, potato-26c");
        System.out.println("  Example2: input-groceries: apple-50c, banana-40c, tomato-1aws 60c, potato-2aws 50c");
        System.out.println();
        System.out.println("2. input-deals command");
        System.out.println("  Example: input-deals: two for three - apple, banana, tomato; buy 1 get 1 half price - potato");
        System.out.println();
        System.out.println("3. customer-basket command");
        System.out.println("  Example: customer-basket: apple, banana, banana, potato, tomato, banana, potato");
        System.out.println();
        System.out.println("4. show-interface:");
        System.out.println("5. quit:");
    }
}
