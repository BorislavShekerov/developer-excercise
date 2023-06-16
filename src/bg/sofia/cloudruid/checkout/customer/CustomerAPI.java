package bg.sofia.cloudruid.checkout.customer;

public interface CustomerAPI {

    /**
     * Adds a product to the cart.
     *
     * @param productName name of the product
     * @param quantity    quantity of the product
     * @throws IllegalArgumentException if any of the parameters are null or empty or quantity is negative.
     */
    void addToCart(String productName, int quantity);

    /**
     * Removes a product from the cart.
     *
     * @param productName name of the product
     * @param quantity    quantity of the product
     * @throws IllegalArgumentException if any of the parameters are null or empty or the quantity is negative.
     */
    void removeFromCart(String productName, int quantity);

    /**
     * Gives the total price of the cart. The price is calculated based on the discounts.
     */
    int checkout();

    /**
     * Lists all products in the cart.
     */
    void listCart();
}
