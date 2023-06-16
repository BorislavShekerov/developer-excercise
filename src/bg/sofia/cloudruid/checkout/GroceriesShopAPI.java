package bg.sofia.cloudruid.checkout;

import bg.sofia.cloudruid.checkout.discount.BuyOneGetHalfOff;
import bg.sofia.cloudruid.checkout.discount.TwoForThreeDiscount;
import bg.sofia.cloudruid.checkout.exception.InvalidProductException;
import bg.sofia.cloudruid.checkout.product.Product;

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
     * @return all products in the shop.
     */
    Set<Product> getProducts();

    /**
     * @return the two for three discounts.
     */
    Set<TwoForThreeDiscount> getDiscountsTwoForThree();

    /**
     * @return the buy one get next on half price discounts.
     */
    Set<BuyOneGetHalfOff> getDiscountsBuyOneGetHalfOff();

}
