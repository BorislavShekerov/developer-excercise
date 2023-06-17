package bg.sofia.cloudruid.checkout.cost;

import bg.sofia.cloudruid.checkout.GroceriesShop;
import bg.sofia.cloudruid.checkout.GroceriesShopAPI;
import bg.sofia.cloudruid.checkout.customer.Customer;
import bg.sofia.cloudruid.checkout.customer.CustomerAPI;
import bg.sofia.cloudruid.checkout.exception.InvalidProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTests {

    GroceriesShopAPI groceriesShop;
    CustomerAPI customer;

    @BeforeEach
    public void setUp() {
        groceriesShop = new GroceriesShop();
        groceriesShop.addProduct("apple", 50);
        groceriesShop.addProduct("banana", 40);
        groceriesShop.addProduct("tomato", 30);
        groceriesShop.addProduct("potato", 26);
        groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato");
        groceriesShop.addDiscountBuyOneGetHalfOff("potato");
        customer = new Customer(groceriesShop);
    }


    @Test
    public void testAddToCartShouldThrowWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> customer.addToCart("", 1), "Invalid parameters");
        assertThrows(IllegalArgumentException.class, () -> customer.addToCart(null, 1), "Invalid parameters");
        assertThrows(IllegalArgumentException.class, () -> customer.addToCart("apple", 0), "Invalid parameters");
        assertThrows(IllegalArgumentException.class, () -> customer.addToCart("apple", -1), "Invalid parameters");
    }

    @Test
    public void testAddToCartShouldThrowWhenProductDoesntExist() {
        assertThrows(InvalidProductException.class, () -> customer.addToCart("motato", 1), "Product not found");
        assertThrows(InvalidProductException.class, () -> customer.addToCart("logato", 1), "Product not found");
        assertThrows(InvalidProductException.class, () -> customer.addToCart("kogato", 2), "Product not found");
        assertThrows(InvalidProductException.class, () -> customer.addToCart("aple", 4), "Product not found");
    }

    @Test
    public void testAddToCartShouldWorkCorrectly() {
        customer.addToCart("apple", 1);
        customer.addToCart("potato", 1);
        customer.addToCart("apple", 2);
        customer.addToCart("tomato", 4);
        assertEquals(8, customer.getCartSize(), "product should be added");
    }

    @Test
    public void testRemoveFromCartShouldThrowWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> customer.removeFromCart("", 1), "Invalid parameters");
        assertThrows(IllegalArgumentException.class, () -> customer.removeFromCart(null, 1), "Invalid parameters");
        assertThrows(IllegalArgumentException.class, () -> customer.removeFromCart("apple", -1), "Invalid parameters");
    }

    @Test
    public void testRemoveFromCartShouldWorkCorrectlyWhenThereIsEnoughQuantity() {
        customer.addToCart("apple", 1);
        customer.addToCart("potato", 1);
        customer.addToCart("apple", 2);
        customer.addToCart("tomato", 4);
        customer.removeFromCart("apple", 1);
        assertEquals(7, customer.getCartSize(), "product should be removed");
    }

    @Test
    public void testRemoveFromCartShouldWorkCorrectlyWhenThereIsNotEnoughQuantity() {
        customer.addToCart("apple", 1);
        customer.addToCart("potato", 1);
        customer.addToCart("apple", 2);
        customer.addToCart("tomato", 4);
        customer.removeFromCart("apple", 10);
        assertEquals(5, customer.getCartSize(), "product should be removed");
    }

    @Test
    public void testRemoveFromCartShouldThrowWhenProductIsNotInTheCart() {
        customer.addToCart("apple", 1);
        customer.addToCart("potato", 1);
        customer.addToCart("apple", 2);
        customer.addToCart("tomato", 4);
        assertThrows(InvalidProductException.class, () -> customer.removeFromCart("maple", 10));
    }

    @Test
    public void testCheckoutShouldWorkCorrectly() {
        customer.addToCart("apple", 1);
        customer.addToCart("banana", 2);
        customer.addToCart("potato", 1);
        customer.addToCart("tomato", 1);
        customer.addToCart("banana", 1);
        customer.addToCart("potato", 1);
        assertEquals("1.99 aws", customer.checkout(), "checkout should work correctly");
    }

    @Test
    public void testCheckoutShouldWorkCorrectlyWithOutAnyDiscounts() {
        customer.addToCart("apple", 1);
        customer.addToCart("potato", 1);
        customer.addToCart("tomato", 1);
        assertEquals("1.06 aws", customer.checkout(), "checkout should work correctly");
    }

    @Test
    public void testCheckoutShouldWorkCorrectlyWithHalfPriceDiscount() {
        customer.addToCart("potato", 11);
        assertEquals("2.21 aws", customer.checkout(), "checkout should work correctly");
    }

    @Test
    public void testCheckoutShouldWorkCorrectlyWithBuyTwoGetOneFreeDiscount() {
        customer.addToCart("tomato", 1);
        customer.addToCart("banana", 1);
        customer.addToCart("apple", 1);
        customer.addToCart("tomato", 1);
        customer.addToCart("banana", 1);
        customer.addToCart("apple", 1);
        customer.addToCart("tomato", 1);
        customer.addToCart("banana", 1);
        customer.addToCart("apple", 1);
        assertEquals("2.70 aws", customer.checkout(), "checkout should work correctly");
    }

    @Test
    public void testCheckoutShouldWorkCorrectlyWithBuyTwoGetOneFreeDiscountButDiffrentOrder() {
        customer.addToCart("tomato", 3);
        customer.addToCart("banana", 3);
        customer.addToCart("apple", 3);
        assertEquals("2.40 aws", customer.checkout(), "checkout should work correctly");
    }
}
