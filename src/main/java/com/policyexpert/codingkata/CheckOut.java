package com.policyexpert.codingkata;

import com.policyexpert.codingkata.model.SKU;
import com.policyexpert.codingkata.rule.PricePerKiloRule;
import com.policyexpert.codingkata.rule.PricingRule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckOut {
    private Map<SKU,Double> shoppingCart = new HashMap<>();
    private BigDecimal totalCartValue = BigDecimal.ZERO;

    private final List<PricingRule> rules;

    public CheckOut(List<PricingRule> rules){
        this.rules = rules;
    }

    public BigDecimal totalCost(){
        shoppingCart.entrySet().forEach(entry-> rules.forEach(rule-> {
            if(rule.applicable(entry.getKey())){
                totalCartValue = totalCartValue.add(rule.computePrice(entry.getValue()));
            }
        }));
        return totalCartValue.setScale(2, RoundingMode.HALF_EVEN);
    }



    public void scan(SKU product,Double itemQuantity){
        if(shoppingCart.containsKey(product)){
            final Double quantity = shoppingCart.get(product);
            shoppingCart.put(product,quantity + itemQuantity);
        }else{
            shoppingCart.put(product,itemQuantity);
        }
    }
}
