package starter;

import entity.AdministratorFantastico;
import entity.AdministratorShop;
import entity.FantasticoClient;
import entity.ShopClient;
import service.FantasticoGroceryShopService;

public class Start {

    public static void main(String[] args) {
        AdministratorShop administratorShop = new AdministratorFantastico();
        administratorShop.insertProducts();

        ShopClient client = new FantasticoClient();
        client.shop(new FantasticoGroceryShopService());
    }
}
