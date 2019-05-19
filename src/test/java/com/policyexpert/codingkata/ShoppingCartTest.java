package com.policyexpert.codingkata;
import com.policyexpert.codingkata.model.SKU;
import com.policyexpert.codingkata.rule.MultiItemsDiscountRule;
import com.policyexpert.codingkata.rule.PricePerKiloRule;
import com.policyexpert.codingkata.rule.PricingRule;
import com.policyexpert.codingkata.rule.XForThePriceOfYRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class ShoppingCartTest {

    private CheckOut checkOut;

    @Test
    public void testShoppingCartTotal() throws Exception{
        List<PricingRule> rules = new ArrayList<>();
        final PricingRule pricePerKiloRule = setupPricePerKiloRule();
        final PricingRule xForThePriceOfYRule = setupXForThePriceOfYRule();
        final PricingRule multiItemsDiscountRule = setupMultiItemsDiscountRule();

        rules.add(pricePerKiloRule);
        rules.add(xForThePriceOfYRule);
        rules.add(multiItemsDiscountRule);
        checkOut = new CheckOut(rules);

        checkOut.scan(SKU.BEANS,1.0);
        checkOut.scan(SKU.BEANS,1.0);
        checkOut.scan(SKU.BEANS,1.0);
        checkOut.scan(SKU.COKE,1.0);
        checkOut.scan(SKU.COKE,1.0);
        checkOut.scan(SKU.ORANGES,0.2);

        assertEquals(new BigDecimal("2.40"),checkOut.totalCost());

        assertNull(pricePerKiloRule.getSavingsSummary());
        assertEquals("BEANS 3 for 2       -0.50",xForThePriceOfYRule.getSavingsSummary());
        assertEquals("COKE 2 for £1       -0.40",multiItemsDiscountRule.getSavingsSummary());
    }


    private PricePerKiloRule setupPricePerKiloRule() throws Exception{
        List<SKU> productListForPricePerKilo = new ArrayList<>();
        productListForPricePerKilo.add(SKU.ORANGES);
        return PricePerKiloRule.builder()
                .setProductList(productListForPricePerKilo)
                .setUnitPrice(new BigDecimal("1.99"))
                .build();
    }

    private XForThePriceOfYRule setupXForThePriceOfYRule() throws Exception{
        List<SKU> productListForXForThePriceOfYRule = new ArrayList<>();
        productListForXForThePriceOfYRule.add(SKU.BEANS);
        return XForThePriceOfYRule.builder()
                .setProductList(productListForXForThePriceOfYRule)
                .setX(3)
                .setY(2)
                .setUnitPrice(new BigDecimal("0.50"))
                .build();
    }

    private MultiItemsDiscountRule setupMultiItemsDiscountRule() throws Exception{
        List<SKU> productListForMultiItemsDiscountRule = new ArrayList<>();
        productListForMultiItemsDiscountRule.add(SKU.COKE);
        return MultiItemsDiscountRule.builder()
                .setX(2)
                .setY(new BigDecimal("1"))
                .setProductList(productListForMultiItemsDiscountRule)
                .setUnitPrice(new BigDecimal("0.70"))
                .build();
    }


}
