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

        groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato");

        groceriesShop.addDiscountBuyOneGetHalfOff("potato");

        CustomerAPI customer = new Customer(groceriesShop);

        customer.addToCart("apple", 1);
        customer.addToCart("banana", 2);
        customer.addToCart("potato", 1);
        customer.addToCart("tomato", 1);
        customer.addToCart("banana", 1);
        customer.addToCart("potato", 1);

        System.out.println(customer.checkout());

    }
}