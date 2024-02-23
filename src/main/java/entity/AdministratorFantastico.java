package entity;

import service.FantasticoGroceryShopService;

public class AdministratorFantastico implements AdministratorShop{

    private FantasticoGroceryShopService fantasticoGroceryShopService;

    public AdministratorFantastico () {
        fantasticoGroceryShopService = new FantasticoGroceryShopService();
    }

    @Override
    public void insertProducts() {
        fantasticoGroceryShopService.insertProducts();
    }

}
