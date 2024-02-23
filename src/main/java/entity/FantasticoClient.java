package entity;


import service.ShopService;

public class FantasticoClient implements ShopClient{

    private ClientBasket clientBasket;

    public FantasticoClient () {
        clientBasket = new ClientBasket();
    }

    @Override
    public void shop(ShopService shop) {
        shop.shop(this.clientBasket);
    }


}
