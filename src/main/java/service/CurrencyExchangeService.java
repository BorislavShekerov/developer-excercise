package service;


import java.math.BigDecimal;

public class CurrencyExchangeService implements CurrencyExchange {

    public BigDecimal exchangeCloudsToAws(Double cloudAmount) {
        return BigDecimal.valueOf(cloudAmount / 100).setScale(2);
    }

}
