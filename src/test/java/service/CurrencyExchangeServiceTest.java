package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CurrencyExchangeServiceTest {

    private CurrencyExchangeService service;

    @Before
    public void setUp() {
        service = new CurrencyExchangeService();
    }

    @Test
    public void exchangeCloudsToAwsShouldReturnCorrectValueIfSmallerThanOneAws() {
        String expect = "0.66";
        Double clouds = 66.0;
        BigDecimal bigDecimal = service.exchangeCloudsToAws(clouds);
        Assert.assertEquals(expect,bigDecimal.toString());
    }

    @Test
    public void exchangeCloudsToAwsShouldReturnCorrectValueIfBiggerThanOneAws() {
        String expect = "1.02";
        Double clouds = 102.0;
        BigDecimal bigDecimal = service.exchangeCloudsToAws(clouds);
        Assert.assertEquals(expect,bigDecimal.toString());
    }
}
