package com.policyexpert.codingkata;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class ShoppingCartTest {

    private CheckOut checkOut;

    @Test
    public void testShoppingCartTotal() throws Exception{
        checkOut = new CheckOut();
        assertEquals(new BigDecimal("0"),checkOut.totalCost());
    }



}
