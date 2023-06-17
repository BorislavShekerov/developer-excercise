package bg.sofia.cloudruid.checkout;

import bg.sofia.cloudruid.checkout.exception.InvalidProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroceriesShopTests {

    GroceriesShopAPI groceriesShop;

    @BeforeEach
    public void setUp() {
        groceriesShop = new GroceriesShop();
    }

    @Test
    public void testAddProductShouldThrowWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addProduct("", 1),
            "Product name cannot be empty");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addProduct(null, 1),
            "Product name cannot be null");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addProduct(" ", 1),
            "Product name cannot be blank");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addProduct("apple", -1),
            "Product price cannot be negative");
    }


    @Test
    public void testAddProductShouldReturnFalseIfProductAlreadyExists() {
        groceriesShop.addProduct("apple", 1);
        assertFalse(groceriesShop.addProduct("apple", 5),
            "Product already exists");
    }

    @Test
    public void testAddProductShouldWorkCorrectlyWithCorrectParameters() {
        assertTrue(groceriesShop.addProduct("apple", 1),
            "Product should be added successfully");
        assertTrue(groceriesShop.addProduct("banana", 5),
            "Product should be added successfully");
    }

    @Test
    public void testAddDiscountTwoForThreeShouldThrowWithInvalidParameters() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 5);
        groceriesShop.addProduct("tomato", 10);


        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountTwoForThree("", "banana", "tomato"),
            "Product name cannot be empty");
        assertThrows(IllegalArgumentException.class,
            () -> groceriesShop.addDiscountTwoForThree(null, "banana", "tomato"),
            "Product name cannot be null");
        assertThrows(IllegalArgumentException.class,
            () -> groceriesShop.addDiscountTwoForThree(" ", "banana", "tomato"),
            "Product name cannot be blank");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountTwoForThree("apple", "", "tomato"),
            "Product name cannot be empty");
        assertThrows(IllegalArgumentException.class,
            () -> groceriesShop.addDiscountTwoForThree("apple", null, "tomato"),
            "Product name cannot be null");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountTwoForThree("apple", " ", "tomato"),
            "Product name cannot be blank");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountTwoForThree("apple", "banana", ""),
            "Product name cannot be empty");
        assertThrows(IllegalArgumentException.class,
            () -> groceriesShop.addDiscountTwoForThree("apple", "banana", null),
            "Product name cannot be null");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountTwoForThree("apple", "banana", " "),
            "Product name cannot be blank");
    }

    @Test
    public void testAddDiscountTwoForThreeShouldThrowWithInvalidProducts() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 1);

        assertThrows(
            InvalidProductException.class, () -> groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato"),
            "Product does not exist");
    }

    @Test
    public void testAddDiscountTwoFroThreeShouldWorkCorrectly() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 1);
        groceriesShop.addProduct("tomato", 1);
        groceriesShop.addProduct("potato", 1);
        groceriesShop.addProduct("carrot", 1);

        assertTrue(groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato"),
            "Discount should be added successfully");
    }

    @Test
    public void testAddDiscountTwoForThreeShouldReturnFalseIfAnyOfTheProductsIsAlreadyInADifferentDiscount() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 1);
        groceriesShop.addProduct("tomato", 1);
        groceriesShop.addProduct("potato", 1);
        groceriesShop.addProduct("carrot", 1);


        groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato");
        assertFalse(groceriesShop.addDiscountTwoForThree("apple", "potato", "carrot"),
            "apple is already in a different discount");
    }

    @Test
    public void testAddDiscountBuyOneGetHalfOffShouldThrowWithInvalidParameters() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 5);
        groceriesShop.addProduct("tomato", 10);

        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountBuyOneGetHalfOff(""),
            "Product name cannot be empty");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountBuyOneGetHalfOff(null),
            "Product name cannot be null");
        assertThrows(IllegalArgumentException.class, () -> groceriesShop.addDiscountBuyOneGetHalfOff(" "),
            "Product name cannot be blank");
    }

    @Test
    public void testAddDiscountBuyOneGetHalfOffShouldThrowIfProductDoesNotExist() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 5);
        groceriesShop.addProduct("tomato", 10);

        assertThrows(InvalidProductException.class, () -> groceriesShop.addDiscountBuyOneGetHalfOff("aple"),
            "Product name cannot be empty");
        assertThrows(InvalidProductException.class, () -> groceriesShop.addDiscountBuyOneGetHalfOff("banan"),
            "Product name cannot be null");
        assertThrows(InvalidProductException.class, () -> groceriesShop.addDiscountBuyOneGetHalfOff("tomate"),
            "Product name cannot be blank");
    }

    @Test
    public void testAddDiscountBuyOneGetHalfOffShouldReturnFalseIfTheProductIsAlreadyInADifferentDiscount() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 5);
        groceriesShop.addProduct("tomato", 10);

        groceriesShop.addDiscountTwoForThree("apple", "banana", "tomato");
        assertFalse(groceriesShop.addDiscountBuyOneGetHalfOff("apple"), "apple is already in a different discount");

    }

    @Test
    public void testAddDiscountBuyOneGetHalfOffShouldReturnFalseIfTheProductIsAlreadyInADifferentDiscountOfTheSameType() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 5);
        groceriesShop.addProduct("tomato", 10);

        groceriesShop.addDiscountBuyOneGetHalfOff("apple");
        assertFalse(groceriesShop.addDiscountBuyOneGetHalfOff("apple"), "apple is already in a different discount");

    }

    @Test
    public void testAddDiscountBuyOneGetHalfOffShouldWorkCorrectly() {
        groceriesShop.addProduct("apple", 1);
        groceriesShop.addProduct("banana", 5);
        groceriesShop.addProduct("tomato", 10);

        assertTrue(groceriesShop.addDiscountBuyOneGetHalfOff("apple"), "Discount should be added successfully");
        assertTrue(groceriesShop.addDiscountBuyOneGetHalfOff("banana"), "Discount should be added successfully");
        assertTrue(groceriesShop.addDiscountBuyOneGetHalfOff("tomato"), "Discount should be added successfully");


    }
}
