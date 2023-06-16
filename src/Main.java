import bg.sofia.cloudruid.checkout.GroceriesShop;
import bg.sofia.cloudruid.checkout.GroceriesShopAPI;
import bg.sofia.cloudruid.checkout.customer.Customer;
import bg.sofia.cloudruid.checkout.customer.CustomerAPI;

public class Main {
    public static void main(String[] args) {
        GroceriesShopAPI groceriesShop = new GroceriesShop();

        groceriesShop.addProduct("apple", 50);
        groceriesShop.addProduct("banana", 40);
        groceriesShop.addProduct("tomato", 30);
        groceriesShop.addProduct("potato", 26);

        System.out.println(groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato"));

        System.out.println(groceriesShop.addDiscountBuyOneGetHalfOff("potato"));

        CustomerAPI customer = new Customer(groceriesShop);

        customer.addToCart("apple", 1);
        customer.addToCart("banana", 3);
        customer.addToCart("potato", 2);
        customer.addToCart("tomato", 1);

        System.out.println(customer.checkout());
    }
}
