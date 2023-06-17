package bg.sofia.cloudruid.checkout;

import bg.sofia.cloudruid.checkout.exception.InvalidProductException;

import java.util.Map;
import java.util.Set;

public interface GroceriesShopAPI {

    /**
     * @param productName name of the product
     * @param price       price of the product
     * @return true if the product is added successfully, false if the product already exists.
     * @throws IllegalArgumentException if any of the parameters are null or empty.
     */
    boolean addProduct(String productName, int price);


    /**
     * @param productName  name of the product
     * @param productName2 name of the product
     * @param productName3 name of the product
     * @return true if the discount is added successfully, false otherwise.
     * @throws IllegalArgumentException if any of the parameters are null or empty.
     * @throws InvalidProductException  if any of the products do not exist.
     */
    boolean addDiscountTwoForThree(String productName, String productName2, String productName3);


    /**
     * @param productName name of the product
     * @return true if the discount is added successfully, false otherwise.
     * @throws IllegalArgumentException if any of the parameters are null or empty.
     * @throws InvalidProductException  if any of the products do not exist.
     */
    boolean addDiscountBuyOneGetHalfOff(String productName);

    /**
     * @return unmodifiable copy of the products.
     */
    Map<String, Integer> getProducts();

    /**
     * @return unmodifiable copy of the products in discount.
     */
    Set<Set<String>> getDiscountsTwoForThree();

    /**
     * @return unmodifiable copy of the products in discount.
     */
    Set<String> getDiscountsBuyOneGetHalfOff();

    /**
     * @param productName name of the product
     * @return true if the product is in discount, false otherwise.
     * @throws IllegalArgumentException if any of the parameters are null or empty.
     */
    boolean isInDiscount(String productName);

}
